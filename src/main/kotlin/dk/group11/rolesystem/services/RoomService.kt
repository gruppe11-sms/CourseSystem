package dk.group11.rolesystem.services

import dk.group11.rolesystem.models.Room
import dk.group11.rolesystem.repositories.RoomRepository
import org.springframework.stereotype.Service

@Service
class RoomService(private val roomRepository: RoomRepository) {

    fun deleteRoom(roomId: Long) {
        roomRepository.delete(roomId)
    }

    fun getRoom(roomId: Long): Room {
        return roomRepository.findOne(roomId)
    }

    fun updateRoom(room: Room) {
        roomRepository.save(room)
    }

    fun findAllRooms(): Iterable<Room> {
        return roomRepository.findAll()
    }
}