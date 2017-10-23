package dk.group11.rolesystem.Controllers

import dk.group11.rolesystem.Models.Lesson
import dk.group11.rolesystem.Services.LessonService
import org.springframework.web.bind.annotation.*

@RestController
class LessonController(val lessonService: LessonService) {
    @GetMapping("/api/courses/lessons")
    fun getLessons(): Iterable<Lesson> {
        return lessonService.findAllLessons()
    }

    @GetMapping("/api/courses/lessons/{lessonId}")
    fun getLesson(@PathVariable lessonId: Long): Lesson {
        return lessonService.findOneLesson(lessonId)
    }

    @PutMapping("/api/courses/{courseId}/lessons/{lessonId}")
    fun updateLesson(@RequestBody lesson: Lesson, @PathVariable lessonId: Long) {
        lessonService.updateLesson(lesson)
    }

    @DeleteMapping("/api/courses/lessons/{lessonId}")
    fun deleteLesson(@PathVariable lessonId: Long) {
        lessonService.deleteLesson(lessonId)
    }
}