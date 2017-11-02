package dk.group11.coursesystem.repositories

import dk.group11.coursesystem.models.Room
import org.springframework.data.repository.CrudRepository

interface RoomRepository : CrudRepository<Room, Long>