package dk.group11.coursesystem.repositories

import dk.group11.coursesystem.models.ParticipantRole
import org.springframework.data.repository.CrudRepository

interface ParticipantRoleRepository : CrudRepository<ParticipantRole, Long> {
    fun findByKey(key: String): ParticipantRole
}