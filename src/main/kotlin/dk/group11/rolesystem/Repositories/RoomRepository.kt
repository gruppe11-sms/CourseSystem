package dk.group11.rolesystem.Repositories

import dk.group11.rolesystem.Models.Room
import org.springframework.data.repository.CrudRepository

interface RoomRepository : CrudRepository<Room, Long>