package dk.group11.coursesystem.services.fileService.storage

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("storage")
data class FileProperties(var location : String = "storage")