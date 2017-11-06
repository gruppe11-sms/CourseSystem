package dk.group11.coursesystem.repositories

import dk.group11.coursesystem.models.Course
import org.springframework.data.repository.CrudRepository

interface CourseRepository : CrudRepository<Course, Long> {
    fun existsByTitle(title: String): Boolean
    fun findByTitle(title: String): Course
}