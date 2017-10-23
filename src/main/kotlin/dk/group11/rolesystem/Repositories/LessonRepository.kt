package dk.group11.rolesystem.Repositories

import dk.group11.rolesystem.Models.Lesson
import org.springframework.data.repository.CrudRepository


interface LessonRepository : CrudRepository<Lesson,Long>
