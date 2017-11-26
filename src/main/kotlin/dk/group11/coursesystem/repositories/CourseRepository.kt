package dk.group11.coursesystem.repositories

import dk.group11.coursesystem.models.Course
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CourseRepository : CrudRepository<Course, Long>