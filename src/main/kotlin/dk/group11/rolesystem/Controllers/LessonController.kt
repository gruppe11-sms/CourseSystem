package dk.group11.rolesystem.Controllers

import dk.group11.rolesystem.Models.Lesson
import dk.group11.rolesystem.Services.LessonService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/courses")
class LessonController(val lessonService: LessonService) {
    @GetMapping("/lessons")
    fun getLessons(): Iterable<Lesson> {
        return lessonService.findAllLessons()
    }

    @GetMapping("/lessons/{lessonId}")
    fun getLesson(@PathVariable lessonId: Long): Lesson {
        return lessonService.findOneLesson(lessonId)
    }

    @PutMapping("/{courseId}/lessons/{lessonId}")
    fun updateLesson(@RequestBody lesson: Lesson, @PathVariable lessonId: Long) {
        lessonService.updateLesson(lesson)
    }

    @DeleteMapping("/lessons/{lessonId}")
    fun deleteLesson(@PathVariable lessonId: Long) {
        lessonService.deleteLesson(lessonId)
    }
}