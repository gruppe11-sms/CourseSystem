package dk.group11.rolesystem.Controllers

import dk.group11.rolesystem.Models.Course
import dk.group11.rolesystem.Models.Lesson
import dk.group11.rolesystem.Services.CourseService
import dk.group11.rolesystem.Services.LessonService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/courses")
class CourseLessonController(val lessonService: LessonService, val courseService: CourseService) {


    @GetMapping("/{courseId}/lessons")
    fun getLessonsForCourse(@PathVariable courseId: Long): Iterable<Lesson> {
        return courseService.getCourseById(courseId).lessons
    }

    @PostMapping("/{courseId}/lessons")
    fun addLesson(@PathVariable courseId: Long, @RequestBody lesson: Lesson) {
        val course: Course = courseService.getCourseById(courseId)
        course.lessons.add(lesson)
        courseService.saveCourse(course)
    }
}