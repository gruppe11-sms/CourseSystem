package dk.group11.coursesystem.services

import dk.group11.coursesystem.models.Room
import dk.group11.coursesystem.repositories.RoomRepository
import org.springframework.stereotype.Service

@Service
class RoomService(private val roomRepository: RoomRepository) {

    fun deleteRoom(roomId: Long) = roomRepository.delete(roomId)

    fun getRoom(roomId: Long): Room = roomRepository.findOne(roomId)

    fun updateRoom(room: Room): Room = roomRepository.save(room)

    fun findAllRooms(): Iterable<Room> = roomRepository.findAll()
}