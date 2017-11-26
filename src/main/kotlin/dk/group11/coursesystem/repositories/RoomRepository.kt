package dk.group11.coursesystem.repositories

import dk.group11.coursesystem.models.Room
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RoomRepository : CrudRepository<Room, Long>