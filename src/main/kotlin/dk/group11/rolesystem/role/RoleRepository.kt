package dk.group11.rolesystem.role

import org.springframework.data.repository.CrudRepository

interface RoleRepository : CrudRepository<Role, Long> {
    fun findByKey(key: String): Role
}