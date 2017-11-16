package dk.group11.coursesystem.controllers

import dk.group11.coursesystem.models.Participant
import dk.group11.coursesystem.services.CourseService
import dk.group11.coursesystem.services.ParticipantService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/courses")
class ParticipantController(private val participantService: ParticipantService,
                            private val courseService: CourseService) {

    @GetMapping("/participants/{participantId}")
    fun getParticipant(@PathVariable participantId: Long): ParticipantDTO {
        return participantService.findParticipantById(participantId).toDTO()
    }

    @GetMapping("/participants/user/{userId}")
    fun getParticipantsByUserId(@PathVariable userId: Long): List<ParticipantDTO>{
        return participantService.findParticpantsByUserId(userId).map { it.toDTO() }
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