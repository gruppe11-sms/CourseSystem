package dk.group11.rolesystem.course

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

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

    @PutMapping("/api/courses")
    fun updateCourse(@RequestBody course : Course) {
        return courseService.updateCourse(course)
    }

    @DeleteMapping("/api/courses/{id}")
    fun deleteCourse(id : Long){
        return courseService.deleteCourse(id)
    }


}