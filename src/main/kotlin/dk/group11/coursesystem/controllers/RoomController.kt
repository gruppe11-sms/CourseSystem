package dk.group11.coursesystem.controllers

import dk.group11.coursesystem.models.Room
import dk.group11.coursesystem.services.DtoService
import dk.group11.coursesystem.services.RoomService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/courses")
class RoomController(private val roomService: RoomService,
                     private val dtoService: DtoService) {

    @PutMapping("/lessons/rooms")
    fun updateRoom(@RequestBody room: Room) = roomService.updateRoom(room)

    @DeleteMapping("/lessons/rooms/{roomId}")
    fun deleteRoom(@PathVariable roomId: Long) = roomService.deleteRoom(roomId)

    @GetMapping("/lessons/rooms/{roomId}")
    fun getRoom(@PathVariable roomId: Long): RoomDTO = dtoService.convert(roomService.getRoom(roomId))

    @GetMapping("/lessons/rooms")
    fun getAllRooms(): Iterable<RoomDTO> = roomService.findAllRooms().map { dtoService.convert(it) }
}