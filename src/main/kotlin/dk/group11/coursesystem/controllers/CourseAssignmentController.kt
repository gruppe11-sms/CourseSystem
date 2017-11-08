package dk.group11.coursesystem.controllers

import dk.group11.coursesystem.models.Assignment
import dk.group11.coursesystem.services.CourseService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/courses")
class CourseAssignmentController(val courseService: CourseService) {

    @PostMapping("/{courseId}/assignments")
    fun saveAssignment(@PathVariable courseId: Long, @RequestBody assignment: Assignment) {
        var course = courseService.getCourseById(courseId)
        course.participants.forEach { it.assignments.add(assignment) }
        //assignment.participants.addAll(course.participants)
        course.assignments.add(assignment)
        assignment.course = course
        courseService.saveCourse(course)
    }
}