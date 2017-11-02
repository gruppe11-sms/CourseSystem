package dk.group11.rolesystem.repositories

import dk.group11.rolesystem.models.Room
import org.springframework.data.repository.CrudRepository

interface RoomRepository : CrudRepository<Room, Long>