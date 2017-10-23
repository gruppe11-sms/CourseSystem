package dk.group11.rolesystem.Controllers

import dk.group11.rolesystem.Models.Room
import dk.group11.rolesystem.Services.LessonService
import dk.group11.rolesystem.Services.RoomService
import org.springframework.web.bind.annotation.*


@RestController
class LessonRoomController(val lessonService: LessonService,
                           val roomService: RoomService) {
    @GetMapping("/api/courses/lessons/{lessonId}/rooms")
    fun getRooms(@PathVariable lessonId: Long): Iterable<Room> {
        return lessonService.findOneLesson(lessonId).room
    }

    @PostMapping("/api/courses/lessons/{lessonId}/rooms")
    fun addRoom(@PathVariable lessonId: Long, @RequestBody room: Room) {
        val lesson = lessonService.findOneLesson(lessonId)
        lesson.room.add(room)
        lessonService.updateLesson(lesson)
    }
}
