package dk.group11.rolesystem.Controllers

import dk.group11.rolesystem.Models.Assignment
import dk.group11.rolesystem.Services.AssignmentService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/courses")
class AssignmentController(val assignmentService: AssignmentService) {

    @GetMapping("{courseId}/assignments")
    fun getCourseAssignments(@PathVariable courseId: Long): Iterable<Assignment> {
        return assignmentService.getAssignments(courseId)
    }

    @GetMapping("/assignments/{assignmentId}")
    fun getOneAssignment(@PathVariable assignmentId: Long): Assignment {
        return assignmentService.getOneAssignment(assignmentId)
    }

    @GetMapping("/assignments")
    fun getAllAssignments(): MutableIterable<Assignment>? {
        return assignmentService.getAllAssignments()
    }

    @DeleteMapping("/assignments/{assignmentId}")
    fun deleteAssignment(@PathVariable assignmentId: Long) {
        assignmentService.deleteAssignment(assignmentId)
    }

    @PostMapping("/{courseId}/assignments")
    fun saveAssignment(@PathVariable courseId: Long, @RequestBody assignment: Assignment) {
        assignmentService.addAssignment(courseId, assignment)
    }

    @PutMapping("/{courseId}/assignments/{assignmentId}")
    fun updateAssignment(@PathVariable assignmentId: Long, @RequestBody assignment: Assignment) {
        assignmentService.updateAssignment(assignmentId, assignment)
    }
}