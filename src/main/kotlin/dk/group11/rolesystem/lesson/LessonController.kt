package dk.group11.rolesystem.lesson

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class LessonController(){

    @Autowired
    val lessonService = LessonService()

    @GetMapping("/api/courses/{courseid}/lessons")
    fun getLessons(@PathVariable courseid : Long){
        lessonService.findAllLessons()
    }

    @GetMapping("/api/courses/{courseid}/lessons/{lessonid}")
    fun getLesson(@PathVariable lessonid: Long){
        lessonService.findLesson(lessonid)
    }

    @PostMapping("/api/courses/{courseid}/lessons")
    fun addLesson(@RequestBody lesson : Lesson){
        lessonService.addLesson(lesson)
    }

    @PutMapping("/api/courses/{courseid}/lessons/{lessonid}")
    fun updateLesson(@RequestBody lesson : Lesson,@PathVariable lessonid: Long){
        lessonService.updateLesson(lesson)
    }

    @DeleteMapping ("/api/courses/{courseid}/lessons/{lessonid}")
    fun deleteLesson(@PathVariable lessonid : Long){
        lessonService.deletelesson(lessonid)
    }



}