package dk.group11.coursesystem.repositories

import dk.group11.coursesystem.models.Participant
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ParticipantRepository : CrudRepository<Participant, Long> {
    fun findByUserId(userid: Long): Iterable<Participant>
}