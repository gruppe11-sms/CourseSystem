package dk.group11.rolesystem.controllers

import dk.group11.rolesystem.models.Course
import dk.group11.rolesystem.models.Lesson
import dk.group11.rolesystem.services.CourseService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/courses")
class CourseLessonController(val courseService: CourseService) {


    @GetMapping("/{courseId}/lessons")
    fun getLessonsForCourse(@PathVariable courseId: Long): Iterable<LessonDTO> {
        return courseService.getCourseById(courseId).lessons.map { it.toDTO() }
    }

    @PostMapping("/{courseId}/lessons")
    fun addLesson(@PathVariable courseId: Long, @RequestBody lesson: Lesson) {
        val course: Course = courseService.getCourseById(courseId)
        course.lessons.add(lesson)
        courseService.saveCourse(course)
    }
}