package dk.group11.coursesystem.controllers

import dk.group11.coursesystem.services.CourseService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/courses")
class CourseLessonController(val courseService: CourseService) {


}