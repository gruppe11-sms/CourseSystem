package dk.group11.coursesystem.controllers

import dk.group11.coursesystem.models.Lesson
import dk.group11.coursesystem.services.LessonService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/courses")
class LessonController(val lessonService: LessonService) {
    @GetMapping("/lessons")
    fun getLessons(): Iterable<LessonDTO> {
        return lessonService.findAllLessons().map { it.toDTO() }
    }

    @GetMapping("/lessons/{lessonId}")
    fun getLesson(@PathVariable lessonId: Long): LessonDTO {
        return lessonService.findOneLesson(lessonId).toDTO()
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