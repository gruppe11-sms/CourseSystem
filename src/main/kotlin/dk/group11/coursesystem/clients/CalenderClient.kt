package dk.group11.coursesystem.clients

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.core.response
import com.github.kittinunf.result.Result
import dk.group11.coursesystem.models.Activity
import dk.group11.coursesystem.security.HEADER_STRING
import dk.group11.coursesystem.security.ISecurityService
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service

private object actityArrayType : TypeReference<List<Activity>>()

@Service
class CalendarClient(private val calendarConfigProperties: CalendarConfigProperties,
                     private val securityService: ISecurityService) {

    private object ActivityType : TypeReference<Activity>()
    private object ActivityDeserializer : ResponseDeserializable<Activity> {
        override fun deserialize(content: String) = ObjectMapper().readValue<Activity>(content, ActivityType)
    }


    private object ActivityListType : TypeReference<List<Activity>>()
    private object ActivityListDeserializer : ResponseDeserializable<List<Activity>> {
        override fun deserialize(content: String) = ObjectMapper().readValue<List<Activity>>(content, ActivityListType)
    }

    fun getActivity(vararg activityId: Long): List<Activity> {
        val (_, response, result) = Fuel.get("${calendarConfigProperties.url}/api/activities/ids", listOf(Pair("ids", activityId.joinToString(","))))
                .header(Pair("Accepts", "application/json"))
                .header(Pair(HEADER_STRING, securityService.getToken()))
                .response(ActivityListDeserializer)
        // TODO proper exception handling


        return when (result) {
            is Result.Success -> {
                result.value
            }
            else -> emptyList()
        }

    }

    fun createActivity(activity: Activity): Activity {
        val (_, _, result) = Fuel.post(path = "${calendarConfigProperties.url}/api/activities")
                .header(Pair(HEADER_STRING, securityService.getToken()))
                .header(Pair("Content-Type", "application/json"))
                .body(ObjectMapper().writeValueAsBytes(activity))
                .response(ActivityDeserializer)

        return when (result) {
            is Result.Success -> {
                result.value
            }
            else -> Activity()
        }
    }

    fun updateActivity(activity: Activity): Activity {
        val (_, _, result) = Fuel.put(path = "${calendarConfigProperties.url}/api/activities")
                .header(Pair(HEADER_STRING, securityService.getToken()))
                .header(Pair("Content-Type", "application/json"))
                .body(ObjectMapper().writeValueAsBytes(activity))
                .response(ActivityDeserializer)

        return when (result) {
            is Result.Success -> {
                result.value
            }
            else -> activity
        }
    }

    fun deleteActivity(activityId: Long) {
        Fuel.delete(path = "${calendarConfigProperties.url}/api/activities/$activityId")
                .header(Pair(HEADER_STRING, securityService.getToken()))
                .header(Pair("Content-Type", "application/json"))
                .responseString()
    }
}


@Configuration
@ConfigurationProperties(prefix = "calender")
data class CalendarConfigProperties(var url: String = "")