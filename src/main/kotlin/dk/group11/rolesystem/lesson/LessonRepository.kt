package dk.group11.rolesystem.lesson

import org.springframework.data.repository.CrudRepository


interface LessonRepository : CrudRepository<Lesson,Long>
 {

}