package dk.group11.coursesystem.services.fileService.storage

import dk.group11.coursesystem.services.fileService.models.FileEntity
import dk.group11.coursesystem.services.fileService.repositories.FileRepository
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*


@Service
class FileService(private val properties: FileProperties, private var fileRepository: FileRepository) : IFileService {


    private val rootLocation: Path = Paths.get(properties.location)

    init {
        Files.createDirectories(rootLocation)
    }

    override fun storeFile(file: MultipartFile): Long {
        val newFileName = UUID.randomUUID().toString()
        try {
            // Save the file to disc
            Files.copy(file.inputStream, this.rootLocation.resolve(newFileName))

            // Save the file to database
            val entry = FileEntity(fileName = newFileName, originalFileName = file.originalFilename)
            return fileRepository.save(entry).id
        } catch (e: IOException) {
            throw StorageException("Failed to store file: '$newFileName'", e)
        }

    }

    override fun loadFile(id: Long): FileResourse {
        val entry = fileRepository.findOne(id) ?: throw NotFoundException()
        try {
            val file = rootLocation.resolve(entry.fileName)
            val resource = UrlResource(file.toUri())

            if (resource.exists() || resource.isReadable) {
                return FileResourse(entry.originalFileName, resource)
            } else {
                throw NotFoundException()
            }
        } catch (e: MalformedURLException) {
            throw StorageException("Could not read file: '${entry.fileName}'", e)
        }

    }
}