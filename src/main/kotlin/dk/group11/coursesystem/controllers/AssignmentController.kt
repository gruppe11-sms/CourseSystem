package dk.group11.coursesystem.controllers

import dk.group11.coursesystem.services.AssignmentService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/courses")
class AssignmentController(private val assignmentService: AssignmentService) {

    @GetMapping("{courseId}/assignments")
    fun getCourseAssignments(@PathVariable courseId: Long): Iterable<AssignmentDTO> =
            assignmentService.getAssignments(courseId).map { it.toDTO() }

    @GetMapping("assignments/{assignmentId}")
    fun getOneAssignment(@PathVariable assignmentId: Long): AssignmentDTO {
        return assignmentService.getAssignment(assignmentId).toDTO(false)
    }

    @GetMapping("assignments")
    fun getAllAssignments(): Iterable<AssignmentDTO> =
            assignmentService.getAllAssignments().map { it.toDTO(false) }

    @DeleteMapping("assignments/{assignmentId}")
    fun deleteAssignment(@PathVariable assignmentId: Long) = assignmentService.deleteAssignment(assignmentId)

    @PostMapping("{courseId}/assignments")
    fun createAssignment(@PathVariable courseId: Long,
                         @RequestBody assignmentDTO: AssignmentDTO): AssignmentDTO {
        return assignmentService.createAssignment(courseId, assignmentDTO).toDTO(false)
    }

    @PutMapping("{courseId}/assignments/{assignmentId}")
    fun updateAssignment(@PathVariable assignmentId: Long,
                         @RequestBody assignmentDTO: AssignmentDTO): AssignmentDTO {

        return assignmentService.updateAssignment(assignmentDTO).toDTO(false)
    }
}