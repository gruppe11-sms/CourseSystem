package dk.group11.coursesystem.repositories

import dk.group11.coursesystem.models.Assignment
import org.springframework.data.repository.CrudRepository
import org.springframework.web.multipart.MultipartFile

interface AssignmentRepository : CrudRepository<Assignment, Long> {
    //fun existsByTitle(title: String): Boolean
    fun findByCourseId(CourseId: Long): Iterable<Assignment>


}