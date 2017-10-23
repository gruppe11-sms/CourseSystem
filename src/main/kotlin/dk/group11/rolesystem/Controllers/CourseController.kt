package dk.group11.rolesystem.Controllers

import dk.group11.rolesystem.Models.Course
import dk.group11.rolesystem.Services.CourseService
import org.springframework.web.bind.annotation.*

@RestController
class CourseController(val courseService: CourseService) {

    @GetMapping("/api")
    fun sayHello(): String {
        return "Hello"
    }

    @GetMapping("/api/courses/{courseId}")
    fun getCourseById(@PathVariable courseId: Long): Course {
        return courseService.getCourseById(courseId)
    }

    @GetMapping("/api/courses")
    fun getCourses(): Iterable<Course> {
        return courseService.getCourses()
    }

    @PostMapping("/api/courses")
    fun addCourse(@RequestBody course: Course) {
        return courseService.saveCourse(course)
    }

    @PutMapping("/api/courses/{courseId}")
    fun updateCourse(@RequestBody course : Course) {
        return courseService.saveCourse(course)
    }

    @DeleteMapping("/api/courses/{id}")
    fun deleteCourse(id : Long){
        return courseService.deleteCourse(id)
    }
}