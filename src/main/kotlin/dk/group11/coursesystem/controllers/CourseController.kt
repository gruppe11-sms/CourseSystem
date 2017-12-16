package dk.group11.coursesystem.controllers

import dk.group11.coursesystem.COURSE_MANAGER_ROLE
import dk.group11.coursesystem.exceptions.BadRequestException
import dk.group11.coursesystem.models.Course
import dk.group11.coursesystem.security.SecurityService
import dk.group11.coursesystem.services.CourseService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/courses")
class CourseController(private val courseService: CourseService,
                       private val securityService: SecurityService) {

    @GetMapping("/{courseId}")
    fun getCourseById(@PathVariable courseId: Long): CourseDTO = courseService.getCourseById(courseId).toDTO(true)

    @GetMapping
    fun getCourses(): Iterable<CourseDTO> = courseService.getCourses().map { it.toDTO(true) }

    @PostMapping
    fun addCourse(@RequestBody course: Course): CourseDTO {
        securityService.requireRoles(COURSE_MANAGER_ROLE)
        val newCourse = courseService.createCourse(course)
        return newCourse.toDTO(true)
    }

    @PutMapping("/{courseId}")
    fun updateCourse(@PathVariable courseId: Long, @RequestBody course: Course): CourseDTO {
        securityService.requireRoles(COURSE_MANAGER_ROLE)

        if (course.id != courseId) {
            throw BadRequestException("Ids doesn't match")
        }

        val updatedCourse = courseService.updateCourse(course)
        return updatedCourse.toDTO(true)
    }

    @DeleteMapping
    fun deleteCourse(id: Long) {
        securityService.requireRoles(COURSE_MANAGER_ROLE)
        courseService.deleteCourse(id)
    }
}