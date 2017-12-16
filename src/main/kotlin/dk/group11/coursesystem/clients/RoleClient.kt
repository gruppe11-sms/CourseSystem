package dk.group11.coursesystem.clients

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Deserializable
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.core.response
import com.github.kittinunf.result.Result
import dk.group11.coursesystem.security.HEADER_STRING
import dk.group11.coursesystem.security.ISecretService
import dk.group11.coursesystem.security.TOKEN_PREFIX
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class InternalServerError(message: String) : RuntimeException(message)

class AccessDenied : RuntimeException()
class RequestFailed : RuntimeException()

private val JSON_CONTENT_TYPE = Pair("Content-Type", "application/json")

@Service
class RoleClient(private val roleConfigProperties: RoleConfigProperties, private val secretService: ISecretService, private val loginClient: LoginClient) {


    private object UserType : TypeReference<Map<String, String>>()
    private class UserDeserializer : ResponseDeserializable<Map<String, String>> {
        override fun deserialize(content: String) = ObjectMapper().readValue<Map<String, String>>(content, UserType)
    }


    private interface Jsonable {
        fun toJSON(): String = ObjectMapper().writeValueAsString(this)
    }

    private data class assertRoleResponse(var success: Boolean = false)

    private object assetRoleResponseDeserializer : Deserializable<assertRoleResponse> {
        override fun deserialize(response: Response): assertRoleResponse {
            return ObjectMapper().readValue<assertRoleResponse>(response.dataStream, assertRoleResponse::class.java)
        }

    }

    private data class createRoleRequest(val id: String, val title: String, val description: String) : Jsonable

    private var token: String = ""

    private class loginRequest(val username: String, val password: String) : Jsonable


    @Service
    class LoginClient(private val secretService: ISecretService, private val roleConfigProperties: RoleConfigProperties) {

        @Retryable(maxAttempts = 4, backoff = Backoff(delay = 3000))
        fun login(): String {
            LoggerFactory.getLogger(this.javaClass).info("Attempting to login")
            val systemPassword = String(secretService.get("system_password")).trim()
            LoggerFactory.getLogger(this.javaClass).info("system_password " + systemPassword)

            val requestJson = RoleClient.loginRequest(roleConfigProperties.username, systemPassword).toJSON()
            val (request, response, result) = Fuel.post("${roleConfigProperties.url}/login")
                    .header(Pair("Content-Type", "application/json"))
                    .body(requestJson)
                    .responseString()


            when (result) {
                is Result.Success -> {
                    if (response.statusCode == HttpStatus.OK.value()) {
                        val authHeaders = response.headers[HEADER_STRING]
                        if (authHeaders != null) {
                            val token = authHeaders.first()
                            println("Set token: $token")
                            return token
                        }
                    }
                }
                else -> {
                    LoggerFactory.getLogger(this.javaClass).error(response.toString())
                    LoggerFactory.getLogger(this.javaClass).error(result.toString())
                    LoggerFactory.getLogger(this.javaClass).error(request.toString())
                }
            }
            throw InternalServerError("Could not authorize with role system")
        }
    }

    /**
     * Ensures that a given role exists
     */
    @Retryable(maxAttempts = 3, backoff = Backoff(delay = 3000), value = *arrayOf(AccessDenied::class, RequestFailed::class))
    fun ensureRole(key: String, title: String, description: String) {
        val (_, response, result) = Fuel.post(path = "${roleConfigProperties.url}/api/roles")
                .header(JSON_CONTENT_TYPE)
                .header(Pair(HEADER_STRING, "$TOKEN_PREFIX $token"))
                .body(createRoleRequest(key, title, description).toJSON())
                .responseString()

        when (result) {
            is Result.Failure -> {
                token = loginClient.login()
                throw RequestFailed()
            }
            else -> {
                if (response.statusCode == HttpStatus.FORBIDDEN.value()) {
                    token = loginClient.login()
                    throw AccessDenied()
                }
            }
        }

    }

    /**
     * Verifies that a user is assigned a given role
     */
    @Retryable(value = InternalServerError::class, backoff = Backoff(delay = 3000))
    fun hasRoles(authHeader: String, vararg roleKeys: String): Boolean {
        val (_, _, result) = Fuel.get(
                path = "${roleConfigProperties.url}/api/users/verify",
                parameters = listOf(Pair("roles", roleKeys.joinToString(",")))
        )
                .header(Pair(HEADER_STRING, authHeader))
                .response(assetRoleResponseDeserializer)

        when (result) {
            is Result.Failure -> {
                System.err.print(result.error)
                throw InternalServerError("Could not contact role system")
            }
            is Result.Success -> {
                return result.value.success
            }
        }
    }
}

@Configuration
@ConfigurationProperties(prefix = "role")
data class RoleConfigProperties(
        var url: String = "http://localhost:8084",
        var username: String = "",
        var password: String = ""
)