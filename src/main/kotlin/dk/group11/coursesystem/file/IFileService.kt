package dk.group11.coursesystem.file

import org.springframework.web.multipart.MultipartFile
import java.io.InputStream

interface IFileService {

    fun storeFile(file: MultipartFile)

   // fun loadFile(id: Long): FileResourse
}