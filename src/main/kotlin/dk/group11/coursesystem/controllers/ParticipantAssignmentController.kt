package dk.group11.coursesystem.controllers

import dk.group11.coursesystem.models.Assignment
import dk.group11.coursesystem.models.Participant
import dk.group11.coursesystem.services.ParticipantService
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