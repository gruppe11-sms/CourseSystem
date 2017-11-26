package dk.group11.coursesystem.controllers

import dk.group11.coursesystem.models.AssembledLesson
import dk.group11.coursesystem.services.DtoService
import dk.group11.coursesystem.services.LessonService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/courses/{courseId}")
class LessonController(private val lessonService: LessonService,
                       private val dtoService: DtoService) {

    @GetMapping("/lessons")
    fun getLessonsForCourse(@PathVariable courseId: Long): Iterable<LessonDTO> =
            lessonService.getLessons(courseId).map { dtoService.convert(it) }

    @PostMapping("/lessons")
    fun addLesson(@PathVariable courseId: Long, @RequestBody lesson: AssembledLesson): LessonDTO =
            dtoService.convert(lessonService.createLesson(lesson, courseId), recursive = false)

    @GetMapping("/lessons/{lessonId}")
    fun getLesson(@PathVariable lessonId: Long): LessonDTO = dtoService.convert(lessonService.getLesson(lessonId))

    @PutMapping("/lessons/{lessonId}")
    fun updateLesson(@RequestBody lesson: AssembledLesson, @PathVariable lessonId: Long): LessonDTO =
            dtoService.convert(lessonService.updateLesson(lesson, lessonId), recursive = false)

    @DeleteMapping("/lessons/{lessonId}")
    fun deleteLesson(@PathVariable lessonId: Long) = lessonService.deleteLesson(lessonId)

}