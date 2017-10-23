package dk.group11.rolesystem.Repositories

import dk.group11.rolesystem.Models.Role
import org.springframework.data.repository.CrudRepository

interface RoleRepository : CrudRepository<Role, Long> {
    fun findByKey(key: String): Role
}