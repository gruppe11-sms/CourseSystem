package dk.group11.rolesystem.Repositories

import dk.group11.rolesystem.Models.Participant
import org.springframework.data.repository.CrudRepository

interface ParticipantRepository : CrudRepository<Participant, Long> {
    fun findByUserId(userid: Long): Participant
    fun findByCourse_Id(Course_Id: Long)
}