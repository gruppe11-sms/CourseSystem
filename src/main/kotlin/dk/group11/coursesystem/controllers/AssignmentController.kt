package dk.group11.coursesystem.controllers

import dk.group11.coursesystem.models.Assignment
import dk.group11.coursesystem.models.Participant
import dk.group11.coursesystem.services.AssignmentService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/courses")
class AssignmentController(val assignmentService: AssignmentService) {

    @GetMapping("{courseId}/assignments")
    fun getCourseAssignments(@PathVariable courseId: Long): Iterable<AssignmentDTO> {
        return assignmentService.getAssignments(courseId).map { it.toDTO() }
    }

    @GetMapping("assignments/{assignmentId}")
    fun getOneAssignment(@PathVariable assignmentId: Long): AssignmentDTO {
        return assignmentService.getOneAssignment(assignmentId).toDTO()
    }

    @GetMapping("assignments")
    fun getAllAssignments(): Iterable<AssignmentDTO> {
        return assignmentService.getAllAssignments().map { it.toDTO() }
    }

    @DeleteMapping("assignments/{assignmentId}")
    fun deleteAssignment(@PathVariable assignmentId: Long) {
        assignmentService.deleteAssignment(assignmentId)
    }

    @PostMapping("{courseId}/assignments")
    fun createAssignment(@PathVariable courseId: Long, @RequestBody assignment: Assignment): AssignmentDTO {
        return assignmentService.createAssignment(courseId, assignment).toDTO(false)
    }

    @PutMapping("{courseId}/assignments/{assignmentId}")
    fun updateAssignment(@PathVariable assignmentId: Long, @RequestBody assignment: Assignment): AssignmentDTO {
        return assignmentService.updateAssignment(assignmentId, assignment).toDTO(false)
    }

    @PostMapping("assignments/{assignmentId}")
    fun uploadAssignment(@PathVariable assignmentId: Long,@RequestBody participantId: Long, file: MultipartFile){
        return assignmentService.uploadAssignment(assignmentId, participantId, file)
    }

}