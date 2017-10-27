package dk.group11.rolesystem.Repositories

import dk.group11.rolesystem.Models.Course
import org.springframework.data.repository.CrudRepository

interface CourseRepository : CrudRepository<Course, Long> {
    fun findByTitle(title: String): Course;
}