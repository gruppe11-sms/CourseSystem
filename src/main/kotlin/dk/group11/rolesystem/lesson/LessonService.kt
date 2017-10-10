package dk.group11.rolesystem.lesson

import org.springframework.stereotype.Service

@Service
class LessonService {
    lateinit var lessonrepository : LessonRepository


    fun findAllLessons() : Iterable<Lesson> {
        return lessonrepository.findAll()
    }
    fun findLesson(id : Long) : Lesson {
        return lessonrepository.findOne(id)
    }
    fun addLesson( lesson: Lesson) {
        lessonrepository.save(lesson)
    }
    fun updateLesson (lesson: Lesson){
        lessonrepository.save(lesson)
    }
    fun deletelesson( lessonid : Long){
        lessonrepository.delete(lessonid)
    }
}