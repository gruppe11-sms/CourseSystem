package dk.group11.coursesystem.repositories

import dk.group11.coursesystem.models.ParticipantRole
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ParticipantRoleRepository : CrudRepository<ParticipantRole, Long>