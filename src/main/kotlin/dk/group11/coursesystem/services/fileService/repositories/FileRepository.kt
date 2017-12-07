package dk.group11.coursesystem.services.fileService.repositories

import dk.group11.coursesystem.services.fileService.models.FileEntity
import org.springframework.data.repository.CrudRepository

interface FileRepository : CrudRepository<FileEntity, Long>