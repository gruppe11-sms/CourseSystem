package dk.group11.file.services.storage

import org.springframework.web.multipart.MultipartFile
import java.io.InputStream

interface IFileService {

    fun storeFile(file: MultipartFile)

   // fun loadFile(id: Long): FileResourse
}