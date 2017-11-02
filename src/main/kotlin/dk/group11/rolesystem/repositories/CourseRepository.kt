package dk.group11.rolesystem.repositories

import dk.group11.rolesystem.models.Course
import org.springframework.data.repository.CrudRepository

interface CourseRepository : CrudRepository<Course, Long> {
    fun findByTitle(title: String): Course;
}