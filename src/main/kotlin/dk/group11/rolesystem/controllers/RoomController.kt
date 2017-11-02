package dk.group11.rolesystem.controllers

import dk.group11.rolesystem.models.Room
import dk.group11.rolesystem.services.RoomService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/courses")
class RoomController(val roomService: RoomService) {

    @PutMapping("/lessons/rooms")
    fun updateRoom(@RequestBody room: Room) {
        roomService.updateRoom(room)
    }

    @DeleteMapping("/lessons/rooms/{roomId}")
    fun deleteRoom(@PathVariable roomId: Long) {
        roomService.deleteRoom(roomId)
    }

    @GetMapping("/lessons/rooms/{roomId}")
    fun getRoom(@PathVariable roomId: Long): RoomDTO {
        return roomService.getRoom(roomId).toDTO()
    }

    @GetMapping("/lessons/rooms")
    fun getAllRooms(): Iterable<RoomDTO> {
        return roomService.findAllRooms().map { it.toDTO() }
    }
}