package dk.group11.rolesystem.Repositories

import dk.group11.rolesystem.Models.Assignment
import org.springframework.data.repository.CrudRepository

interface AssignmentRepository : CrudRepository<Assignment, Long> {
    fun findByCourse_Id(Course_Id: Long): Iterable<Assignment>
}