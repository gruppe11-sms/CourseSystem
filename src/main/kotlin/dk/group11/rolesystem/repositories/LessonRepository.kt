package dk.group11.rolesystem.repositories

import dk.group11.rolesystem.models.Lesson
import org.springframework.data.repository.CrudRepository


interface LessonRepository : CrudRepository<Lesson,Long>
