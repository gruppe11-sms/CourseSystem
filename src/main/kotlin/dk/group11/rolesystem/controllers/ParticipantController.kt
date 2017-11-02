package dk.group11.rolesystem.controllers

import dk.group11.rolesystem.models.Participant
import dk.group11.rolesystem.services.CourseService
import dk.group11.rolesystem.services.ParticipantService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/courses")
class ParticipantController(private val participantService: ParticipantService,
                            private val courseService: CourseService) {

    @GetMapping("/participants/{participantId}")
    fun getParticipant(@PathVariable participantId: Long): ParticipantDTO {
        return participantService.findParticipantById(participantId).toDTO()
    }

    @GetMapping("/participants")
    fun getParticipants(): Iterable<ParticipantDTO> {
        return participantService.getAll().map { it.toDTO() }
    }

    @GetMapping("/{courseId}/participants")
    fun getParticipantsForCourse(@PathVariable courseId: Long): Iterable<ParticipantDTO> {
        return courseService.getCourseById(courseId).participants.map { it.toDTO() }
    }

    @PostMapping("/{courseId}/participants")
    fun saveParticipant(@PathVariable courseId: Long, @RequestBody participant: Participant) {
        participantService.saveParticipant(courseId, participant)
    }

    @PutMapping("/{courseId}/participants/{participantId}")
    fun updateParticipant(@PathVariable participantId: Long, @RequestBody participant: Participant) {
        participantService.updateParticipant(participantId, participant)
    }

}