package dk.group11.rolesystem.Controllers

import dk.group11.rolesystem.Models.Assignment
import dk.group11.rolesystem.Services.AssignmentService
import org.springframework.web.bind.annotation.*

@RestController
class AssignmentController(val assignmentService: AssignmentService) {

    @GetMapping("/api/courses/{courseId}/assignments")
    fun getCourseAssignments(@PathVariable courseId: Long): Iterable<Assignment> {
        return assignmentService.getAssignments(courseId)
    }

    @GetMapping("/api/courses/assignments/{assignmentId}")
    fun getOneAssignment(@PathVariable assignmentId: Long): Assignment {
        return assignmentService.getOneAssignment(assignmentId)
    }

    @GetMapping("/api/courses/assignments")
    fun getAllAssignments(): MutableIterable<Assignment>? {
        return assignmentService.getAllAssignments()
    }

    @DeleteMapping("/api/courses/assignments/{assignmentId}")
    fun deleteAssignment(@PathVariable assignmentId: Long) {
        assignmentService.deleteAssignment(assignmentId)
    }

    @PostMapping("/api/courses/{courseId}/assignments")
    fun saveAssignment(@PathVariable courseId: Long, @RequestBody assignment: Assignment) {
        assignmentService.addAssignment(courseId, assignment)
    }

    @PutMapping("/api/courses/{courseId}/assignments/{assignmentId}")
    fun updateAssignment(@PathVariable assignmentId: Long, @RequestBody assignment: Assignment) {
        assignmentService.updateAssignment(assignmentId, assignment)
    }
}