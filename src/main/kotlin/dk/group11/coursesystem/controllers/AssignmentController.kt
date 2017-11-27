package dk.group11.coursesystem.controllers

import dk.group11.coursesystem.models.AssembledAssignment
import dk.group11.coursesystem.services.AssignmentService
import dk.group11.coursesystem.services.DtoService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/courses")
class AssignmentController(private val assignmentService: AssignmentService,
                           private val dtoService: DtoService) {

    @GetMapping("{courseId}/assignments")
    fun getCourseAssignments(@PathVariable courseId: Long): Iterable<AssignmentDTO> =
            assignmentService.getAssignments(courseId).map { dtoService.convert(it) }

    @GetMapping("assignments/{assignmentId}")
    fun getOneAssignment(@PathVariable assignmentId: Long): AssignmentDTO =
            dtoService.convert(assignmentService.getAssignment(assignmentId))

    @GetMapping("assignments")
    fun getAllAssignments(): Iterable<AssignmentDTO> =
            assignmentService.getAllAssignments().map { dtoService.convert(it) }

    @DeleteMapping("assignments/{assignmentId}")
    fun deleteAssignment(@PathVariable assignmentId: Long) = assignmentService.deleteAssignment(assignmentId)

    @PostMapping("{courseId}/assignments")
    fun createAssignment(@PathVariable courseId: Long,
                         @RequestBody assignment: AssembledAssignment): AssignmentDTO =
            dtoService.convert(assignmentService.createAssignment(courseId, assignment))

    @PostMapping("assignments/uploadAssignments")
    fun uploadAssignment(@RequestBody task: UploadTask) {
        println(task.assignmentId + "test")
        val serverTask = ServerTask(task.file, task.assignmentId.toLong())
        return assignmentService.uploadAssignment(serverTask)
    }

    fun updateAssignment(@PathVariable assignmentId: Long,
                         @RequestBody assignment: AssembledAssignment): AssignmentDTO =
            dtoService.convert(assignmentService.updateAssignment(assignmentId, assignment))
}

data class ServerTask(val file: MultipartFile, val assignmentId: Long)
data class UploadTask(val file: MultipartFile, val assignmentId: String)