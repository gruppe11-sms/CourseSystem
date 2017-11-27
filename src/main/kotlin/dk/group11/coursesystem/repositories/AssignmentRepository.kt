package dk.group11.coursesystem.repositories

import dk.group11.coursesystem.models.Assignment
import org.springframework.data.repository.CrudRepository
import org.springframework.web.multipart.MultipartFile
import org.springframework.stereotype.Repository

@Repository
interface AssignmentRepository : CrudRepository<Assignment, Long>