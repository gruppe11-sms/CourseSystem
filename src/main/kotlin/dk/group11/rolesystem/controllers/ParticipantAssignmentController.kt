package dk.group11.rolesystem.controllers

import dk.group11.rolesystem.models.Assignment
import dk.group11.rolesystem.models.Participant
import dk.group11.rolesystem.services.ParticipantService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/courses")
class ParticipantAssignmentController(private val participantService: ParticipantService) {

    @GetMapping("/participants/{participantId}/assignments")
    fun getParticipantAssignments(@PathVariable participantId: Long): Iterable<AssignmentDTO> {
        return participantService.findParticipantById(participantId).assignments.map { it.toDTO() }
    }

    @PostMapping("/api/courses/participants/{participantId}/assignments")
    fun saveAssignment(@PathVariable participantId: Long, @RequestBody assignment: Assignment) {
        val participant: Participant = participantService.findParticipantById(participantId)
        participant.assignments.add(assignment)
        participantService.saveParticipant(participant.course.id, participant)
    }
}