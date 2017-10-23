package dk.group11.rolesystem.Controllers

import dk.group11.rolesystem.Models.Participant
import dk.group11.rolesystem.Services.CourseService
import dk.group11.rolesystem.Services.ParticipantService
import org.springframework.web.bind.annotation.*

@RestController
class ParticipantController(val participantService: ParticipantService, val courseService: CourseService) {

    @GetMapping("/api/courses/{courseId}/participants/{participantId}")
    fun getParticipant(@PathVariable courseId: Long, @PathVariable participantId: Long): Participant {
        return courseService.getCourseById(courseId).participant.first { participant ->
            participant.id == participantId
        }
    }

    @GetMapping("/api/courses/participants")
    fun getParticipants(): MutableIterable<Participant>? {
        return participantService.getAll()
    }

    @GetMapping("/api/course/{courseId}/participants")
    fun getParticipantsForCourse(@PathVariable courseId: Long): MutableList<Participant> {
        return courseService.getCourseById(courseId).participant
    }

    @PostMapping("/api/courses/{courseId}/participants")
    fun saveParticipant(@PathVariable courseId: Long, @RequestBody participant: Participant) {
        participantService.saveParticipant(courseId, participant)
    }

    @PutMapping("/api/courses/{courseId}/participants/{participantId}")
    fun updateParticipant(@PathVariable participantId: Long, @RequestBody participant: Participant) {
        participantService.updateParticipant(participantId, participant)
    }

}