package dk.group11.file.services.storage

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Blob
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*


@Service
class FileService(val fileConfigProperties: FileConfigProperties) : IFileService {
    override fun storeFile(file: MultipartFile) {
        val newFileName = UUID.randomUUID().toString()
        Fuel.upload(fileConfigProperties.url+ "/api/file").blob { request, url ->
            Blob(newFileName, file.size, { file.inputStream })
        }
    }
}

@Configuration
@ConfigurationProperties(prefix = "file")
data class FileConfigProperties(var url: String = "")