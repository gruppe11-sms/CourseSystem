package dk.group11.rolesystem.Controllers

import dk.group11.rolesystem.Models.Room
import dk.group11.rolesystem.Services.RoomService
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
    fun getRoom(@PathVariable roomId: Long): Room {
        return roomService.getRoom(roomId)
    }

    @GetMapping("/lessons/rooms")
    fun getAllRooms(): MutableIterable<Room>? {
        return roomService.findAllRooms()
    }
}