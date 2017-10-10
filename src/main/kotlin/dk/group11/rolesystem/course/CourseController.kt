package dk.group11.rolesystem.course

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class CourseController {


    @Autowired
    val courseService = CourseService()

    @GetMapping("/api/courses/{id}")
    fun getCourseById(@PathVariable id: String): String {
        return ""
    }

    @GetMapping("/api/courses")
    fun getCourses(): Iterable<Course> {
        return courseService.getCourses()
    }
}