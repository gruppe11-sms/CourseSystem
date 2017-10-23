package dk.group11.rolesystem.Services

import dk.group11.rolesystem.Models.Room
import dk.group11.rolesystem.Repositories.RoomRepository
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

    fun findAllRooms(): MutableIterable<Room>? {
        return roomRepository.findAll()
    }
}