package dk.group11.coursesystem.repositories

import dk.group11.coursesystem.models.Lesson
import org.springframework.data.repository.CrudRepository


interface LessonRepository : CrudRepository<Lesson, Long> {
    fun existsByTitle(title: String): Boolean
}
