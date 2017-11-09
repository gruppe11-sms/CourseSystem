package dk.group11.coursesystem.repositories

import dk.group11.coursesystem.models.Participant
import org.springframework.data.repository.CrudRepository

interface ParticipantRepository : CrudRepository<Participant, Long> {
    fun existsByUserId(userId: Long): Boolean
    fun findByUserId(userid: Long): Participant
    fun findByCourse_Id(Course_Id: Long)
}