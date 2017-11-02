package dk.group11.coursesystem.controllers

import dk.group11.coursesystem.models.Course
import dk.group11.coursesystem.services.CourseService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/courses")
class CourseController(val courseService: CourseService) {

    @GetMapping("/test")
    fun sayHello(): String {
        return "Hello world"
    }

    @GetMapping("/{courseId}")
    fun getCourseById(@PathVariable courseId: Long): CourseDTO {
        return courseService.getCourseById(courseId).toDTO()
    }

    @GetMapping
    fun getCourses(): Iterable<CourseDTO> {
        return courseService.getCourses().map { it.toDTO() }
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