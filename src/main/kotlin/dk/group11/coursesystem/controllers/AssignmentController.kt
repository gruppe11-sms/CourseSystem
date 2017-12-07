package dk.group11.coursesystem.controllers

import dk.group11.coursesystem.models.UploadedFile
import dk.group11.coursesystem.security.SecurityService
import dk.group11.coursesystem.services.AssignmentService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/courses")
class AssignmentController(private val assignmentService: AssignmentService,
                           private val securityService: SecurityService) {

    @GetMapping("{courseId}/assignments")
    fun getCourseAssignments(@PathVariable courseId: Long): Iterable<AssignmentDTO> =
            assignmentService.getAssignments(courseId).map { it.toDTO() }

    @GetMapping("assignments/{assignmentId}")
    fun getOneAssignment(@PathVariable assignmentId: Long): AssignmentDTO {
        return assignmentService.getAssignment(assignmentId).toDTO(false)
    }

    @GetMapping("home/assignments")
    fun getComingAssignments(@RequestParam("amount", defaultValue = "") amount: String): Iterable<AssignmentDTO> {
        return assignmentService.getAssignmentsByUserId(id = securityService.getId(), amount = amount.toInt()).map { it.toDTO() }
    }

    @DeleteMapping("assignments/{assignmentId}")
    fun deleteAssignment(@PathVariable assignmentId: Long) = assignmentService.deleteAssignment(assignmentId)

    @PostMapping("{courseId}/assignments")
    fun createAssignment(@PathVariable courseId: Long,
                         @RequestBody assignmentDTO: AssignmentDTO): AssignmentDTO {
        return assignmentService.createAssignment(courseId, assignmentDTO).toDTO(false)
    }

    @PostMapping("assignments/uploadAssignments")
    fun uploadAssignment(@RequestParam("file") file: MultipartFile, @RequestParam("assignmentId") assignmentId: String): UploadedFile {
        println(assignmentId)
        return assignmentService.uploadAssignment(UploadTask(file, assignmentId.toLong()))
    }

    fun updateAssignment(@PathVariable assignmentId: Long,
                         @RequestBody assignmentDTO: AssignmentDTO): AssignmentDTO {

        return assignmentService.updateAssignment(assignmentDTO).toDTO(false)
    }
}

data class UploadTask(val file: MultipartFile, val assignmentId: Long)