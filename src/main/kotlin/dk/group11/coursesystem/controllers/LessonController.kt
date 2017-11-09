package dk.group11.coursesystem.controllers

import dk.group11.coursesystem.exceptions.BadRequestException
import dk.group11.coursesystem.models.Course
import dk.group11.coursesystem.models.Lesson
import dk.group11.coursesystem.services.LessonService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/courses/{courseId}")
class LessonController(val lessonService: LessonService) {

    @GetMapping("/lessons")
    fun getLessonsForCourse(@PathVariable courseId: Long): Iterable<LessonDTO> {
        return lessonService.getLessons(courseId).map { it.toDTO() }
    }

    @PostMapping("/lessons")
    fun addLesson(@PathVariable courseId: Long, @RequestBody lesson: Lesson): LessonDTO {
        return lessonService.createLesson(lesson, courseId).toDTO(false)
    }

    @GetMapping("/lessons/{lessonId}")
    fun getLesson(@PathVariable lessonId: Long): LessonDTO {
        return lessonService.getLesson(lessonId).toDTO()
    }

    @PutMapping("/lessons/{lessonId}")
    fun updateLesson(@RequestBody lesson: Lesson, @PathVariable lessonId: Long): LessonDTO {
        if (lesson.id != lessonId) {
            throw BadRequestException("Lesson id attempted update does not match lessonId URL")
        }
        return lessonService.updateLesson(lesson).toDTO(false)
    }

    @DeleteMapping("/lessons/{lessonId}")
    fun deleteLesson(@PathVariable lessonId: Long) {
        lessonService.deleteLesson(lessonId)
    }
}