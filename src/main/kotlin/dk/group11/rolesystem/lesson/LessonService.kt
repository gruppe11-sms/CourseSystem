package dk.group11.rolesystem.lesson

import java.util.*

//@Service
class LessonService {
    //@Autowired
    //lateinit var lessonrepository : LessonRepository

    fun findAllLessons() : Iterable<Lesson> {
        //  return lessonrepository.findAll()
        return Arrays.asList(Lesson())
    }
    fun findLesson(id : Long) : Lesson {
        //return lessonrepository.findOne(id)
        return Lesson()
    }
    fun addLesson( lesson: Lesson) {
        //lessonrepository.save(lesson)
    }
    fun updateLesson (lesson: Lesson){
        //lessonrepository.save(lesson)
    }
    fun deletelesson( lessonid : Long){
        //lessonrepository.delete(lessonid)
    }
}