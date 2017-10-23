package dk.group11.rolesystem.Controllers

import dk.group11.rolesystem.Models.Room
import dk.group11.rolesystem.Services.RoomService
import org.springframework.web.bind.annotation.*

@RestController
class RoomController(val roomService: RoomService) {

    @PutMapping("/api/courses/lessons/rooms")
    fun updateRoom(@RequestBody room: Room) {
        roomService.updateRoom(room)
    }

    @DeleteMapping("/api/course/lessons/rooms/{roomId}")
    fun deleteRoom(@PathVariable roomId: Long) {
        roomService.deleteRoom(roomId)
    }

    @GetMapping("/api/courses/lessons/rooms/{roomId}")
    fun getRoom(@PathVariable roomId: Long): Room {
        return roomService.getRoom(roomId)
    }

    @GetMapping("/api/courses/lessons/rooms")
    fun getAllRooms(): MutableIterable<Room>? {
        return roomService.findAllRooms()
    }
}