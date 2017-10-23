package dk.group11.rolesystem.Services

import dk.group11.rolesystem.Models.Participant
import dk.group11.rolesystem.Repositories.ParticipantRepository
import org.springframework.stereotype.Service

@Service
class ParticipantService(val participantRepository: ParticipantRepository) {

    fun saveParticipant(courseId: Long, participant: Participant) {
        participant.course.id = courseId
        participantRepository.save(participant)
    }

    fun findParticipantById(participantId: Long): Participant {
        return participantRepository.findOne(participantId)
    }

    fun getAll(): MutableIterable<Participant>? {
        return participantRepository.findAll()
    }

    fun updateParticipant(participantId: Long, participant: Participant): String {
        if (participantRepository.exists(participantId))
            participantRepository.save(participant)
        else
            return "Sorry entry does not exist!"
        return "participant successfully updated!"
    }
}