package dk.group11.coursesystem.clients

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Blob
import com.github.kittinunf.fuel.core.Deserializable
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.response
import com.github.kittinunf.result.Result
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile



@Service
class FileClient(val fileConfigProperties: FileConfigProperties) {

    private data class assertFileResponse(var response: UploadedFile)
    private object fileUploadResponseDeserializer: Deserializable<assertFileResponse>{
        override fun deserialize(response: Response): assertFileResponse {
            return ObjectMapper().readValue<FileClient.assertFileResponse>(response.dataStream, assertFileResponse::class.java)
        }

    }

    fun storeFile(file: MultipartFile): UploadedFile {
        var (_,_,result) = Fuel.upload(fileConfigProperties.url+ "/api/file").blob { request, url ->
            Blob(file.name, file.size, { file.inputStream })
        }.response(fileUploadResponseDeserializer)

        when(result){
            is Result.Failure -> {
                throw InternalServerError("Could not contact file system")
            }
            is Result.Success -> {
                return result.value.response
            }
        }
    }
}

data class UploadedFile(val id: Long)

@Configuration
@ConfigurationProperties(prefix = "file")
data class FileConfigProperties(var url: String = "")