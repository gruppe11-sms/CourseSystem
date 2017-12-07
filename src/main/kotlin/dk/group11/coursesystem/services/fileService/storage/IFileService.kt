package dk.group11.coursesystem.services.fileService.storage

import dk.group11.coursesystem.services.fileService.storage.FileResourse
import org.springframework.web.multipart.MultipartFile

interface IFileService {

    fun storeFile(file: MultipartFile) : Long

    fun loadFile(id: Long): FileResourse
}