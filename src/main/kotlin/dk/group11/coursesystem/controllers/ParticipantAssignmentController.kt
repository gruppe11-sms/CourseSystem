package dk.group11.coursesystem.controllers

import dk.group11.coursesystem.models.AssembledAssignment
import dk.group11.coursesystem.services.DtoService
import dk.group11.coursesystem.services.ParticipantService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/courses")
class ParticipantAssignmentController(private val participantService: ParticipantService,
                                      private val dtoService: DtoService) {

    @GetMapping("/participants/{participantId}/assignments")
    fun getParticipantAssignments(@PathVariable participantId: Long): List<AssignmentDTO> =
            participantService.getAssignmentByParticipantId(participantId).map { dtoService.convert(it) }

    @PostMapping("/api/courses/participants/{participantId}/assignments")
    fun saveAssignment(@PathVariable participantId: Long, @RequestBody assignment: AssembledAssignment) =
            participantService.addAssignmentToParticipant(participantId, assignment)
}