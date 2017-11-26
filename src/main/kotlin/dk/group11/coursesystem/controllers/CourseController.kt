package dk.group11.coursesystem.controllers

import dk.group11.coursesystem.COURSE_CREATOR_ROLE
import dk.group11.coursesystem.COURSE_MANAGEMENT_ROLE
import dk.group11.coursesystem.models.Course
import dk.group11.coursesystem.security.SecurityService
import dk.group11.coursesystem.services.CourseService
import dk.group11.coursesystem.services.DtoService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/courses")
class CourseController(private val courseService: CourseService,
                       private val securityService: SecurityService,
                       private val dtoService: DtoService) {

    @GetMapping("/test")
    fun sayHello(): String = "Hello world"

    @GetMapping("/{courseId}")
    fun getCourseById(@PathVariable courseId: Long): CourseDTO =
            dtoService.convert(courseService.getCourseById(courseId))

    @GetMapping
    fun getCourses(): Iterable<CourseDTO> = courseService.getCourses().map { dtoService.convert(it) }

    @PostMapping
    fun addCourse(@RequestBody course: Course): CourseDTO {
        securityService.requireRoles(COURSE_CREATOR_ROLE)
        val newCourse = courseService.createCourse(course)
        return dtoService.convert(newCourse)
    }

    @PutMapping("/{courseId}")
    fun updateCourse(@PathVariable courseId: Long, @RequestBody course: Course): CourseDTO {
        securityService.requireRoles(COURSE_MANAGEMENT_ROLE)
        val updatedCourse = courseService.updateCourse(course)
        return dtoService.convert(updatedCourse)
    }

    @DeleteMapping
    fun deleteCourse(id: Long) {
        securityService.requireRoles(COURSE_MANAGEMENT_ROLE)
        courseService.deleteCourse(id)
    }
}