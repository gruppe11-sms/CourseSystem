package dk.group11.rolesystem.Controllers

import dk.group11.rolesystem.Models.Course
import dk.group11.rolesystem.Services.CourseService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/courses")
class CourseController(val courseService: CourseService) {

    @GetMapping("/test")
    fun sayHello(): String {
        return "Hello world"
    }

    @GetMapping("/{courseId}")
    fun getCourseById(@PathVariable courseId: Long): Course {
        return courseService.getCourseById(courseId)
    }

    @GetMapping
    fun getCourses(): Iterable<Course> {
        return courseService.getCourses()
    }

    @PostMapping
    fun addCourse(@RequestBody course: Course) {
        println("receiving a course" + course)
        return courseService.saveCourse(course)
    }

    @PutMapping("/{courseId}")
    fun updateCourse(@PathVariable courseId: Long, @RequestBody course : Course) {
        println("receiving a course" + course + "and pathvariable" + courseId)
        return courseService.saveCourse(course)
    }

    @DeleteMapping
    fun deleteCourse(id : Long){
        return courseService.deleteCourse(id)
    }
}