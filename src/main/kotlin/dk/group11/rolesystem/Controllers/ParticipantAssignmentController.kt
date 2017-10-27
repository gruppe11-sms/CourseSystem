package dk.group11.rolesystem.Controllers

import dk.group11.rolesystem.Models.Assignment
import dk.group11.rolesystem.Models.Participant
import dk.group11.rolesystem.Services.AssignmentService
import dk.group11.rolesystem.Services.ParticipantService
import org.springframework.web.bind.annotation.*

@RestController
class ParticipantAssignmentController(private val participantService: ParticipantService,
                                      private val assignmentService: AssignmentService) {

    @GetMapping("/participant/{participantId}/assignments")
    fun getParticipantAssignments(@PathVariable participantId: Long): MutableList<Assignment> {
        return participantService.findParticipantById(participantId).assignments
    }

    @PostMapping("/api/courses/participant/{participantId}/assignment")
    fun saveAssignment(@PathVariable participantId: Long, @RequestBody assignment: Assignment) {
        val participant: Participant = participantService.findParticipantById(participantId)
        participant.assignments.add(assignment)
        participantService.saveParticipant(participant.course.id, participant)
    }
}