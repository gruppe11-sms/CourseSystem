package dk.group11.coursesystem.services

import dk.group11.coursesystem.clients.CalendarClient
import dk.group11.coursesystem.exceptions.BadRequestException
import dk.group11.coursesystem.models.AssembledAssignment
import dk.group11.coursesystem.models.Participant
import dk.group11.coursesystem.repositories.AssignmentRepository
import dk.group11.coursesystem.repositories.ParticipantRepository
import org.springframework.stereotype.Service

@Service
class ParticipantService(private val participantRepository: ParticipantRepository,
                         private val assignmentRepository: AssignmentRepository,
                         private val calendarClient: CalendarClient,
                         private val assignmentService: AssignmentService) {

    fun saveParticipant(courseId: Long, participant: Participant) {
        participant.course.id = courseId
        participantRepository.save(participant)
    }

    fun findParticipantsByUserId(id: Long): List<Participant> {
        return participantRepository.findByUserId(id).toList()
    }

    fun findParticipantById(participantId: Long): Participant {
        return participantRepository.findOne(participantId)
    }

    fun getAll(): Iterable<Participant> {
        return participantRepository.findAll()
    }

    fun updateParticipant(participantId: Long, participant: Participant) {
        if (participantRepository.exists(participantId))
            participantRepository.save(participant)
        else
            throw BadRequestException("participant does not exist")
    }

    fun addAssignmentToParticipant(participantId: Long, assignment: AssembledAssignment) {
        calendarClient.createActivity(assignment.getActivity())
        assignmentRepository.save(assignment.getAssignment())
        val participant = participantRepository.findOne(participantId)
        participant.assignments.add(assignment.getAssignment())
        participantRepository.save(participant)
    }

    fun getAssignmentByParticipantId(participantId: Long): List<AssembledAssignment> {
        return participantRepository.findOne(participantId).assignments
                .map { assignmentService.assemble(it) }
    }
}