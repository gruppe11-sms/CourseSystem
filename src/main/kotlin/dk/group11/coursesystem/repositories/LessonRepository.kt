package dk.group11.coursesystem.repositories

import dk.group11.coursesystem.models.Lesson
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LessonRepository : CrudRepository<Lesson, Long>
