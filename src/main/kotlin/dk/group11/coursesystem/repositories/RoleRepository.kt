package dk.group11.coursesystem.repositories

import dk.group11.coursesystem.models.Role
import org.springframework.data.repository.CrudRepository

interface RoleRepository : CrudRepository<Role, Long> {
    fun findByKey(key: String): Role
}