package dk.group11.rolesystem.participant

import org.springframework.data.repository.CrudRepository

interface ParticipantRepository : CrudRepository<Participant, Long> {
    fun findByCourse_Id(Course_Id: Long)
}