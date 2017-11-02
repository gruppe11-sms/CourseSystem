package dk.group11.rolesystem.repositories

import dk.group11.rolesystem.models.Assignment
import org.springframework.data.repository.CrudRepository

interface AssignmentRepository : CrudRepository<Assignment, Long> {
    fun findByCourseId(CourseId: Long): Iterable<Assignment>
}