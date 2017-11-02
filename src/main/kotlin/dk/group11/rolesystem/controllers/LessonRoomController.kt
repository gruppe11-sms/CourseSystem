package dk.group11.rolesystem.controllers

import dk.group11.rolesystem.models.Room
import dk.group11.rolesystem.services.LessonService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/courses")
class LessonRoomController(val lessonService: LessonService) {
    @GetMapping("/lessons/{lessonId}/rooms")
    fun getRooms(@PathVariable lessonId: Long): Iterable<RoomDTO> {
        return lessonService.findOneLesson(lessonId).room.map { it.toDTO() }
    }

    @PostMapping("/lessons/{lessonId}/rooms")
    fun addRoom(@PathVariable lessonId: Long, @RequestBody room: Room) {
        val lesson = lessonService.findOneLesson(lessonId)
        lesson.room.add(room)
        lessonService.updateLesson(lesson)
    }
}
