package dk.group11.coursesystem.controllers

import dk.group11.coursesystem.models.Course
import dk.group11.coursesystem.models.Lesson
import dk.group11.coursesystem.services.CourseService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/courses")
class CourseLessonController(val courseService: CourseService) {


}