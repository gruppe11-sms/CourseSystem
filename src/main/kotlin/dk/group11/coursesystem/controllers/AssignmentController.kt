package dk.group11.coursesystem.controllers

import dk.group11.coursesystem.models.Assignment
import dk.group11.coursesystem.models.Participant
import dk.group11.coursesystem.models.UploadedFile
import dk.group11.coursesystem.services.AssignmentService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

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

    @PostMapping("assignments/uploadAssignments")
    fun uploadAssignment(@RequestParam("file") file: MultipartFile, @RequestParam("assignmentId") assignmentId: String): UploadedFile {
        return assignmentService.uploadAssignment(UploadTask(file, assignmentId.toLong()))
    }

    fun updateAssignment(@PathVariable assignmentId: Long,
                         @RequestBody assignmentDTO: AssignmentDTO): AssignmentDTO {

        return assignmentService.updateAssignment(assignmentDTO).toDTO(false)
    }
}

data class UploadTask(val file: MultipartFile, val assignmentId: Long)