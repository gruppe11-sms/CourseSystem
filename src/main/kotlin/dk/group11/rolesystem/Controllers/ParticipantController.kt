package dk.group11.rolesystem.Controllers

import dk.group11.rolesystem.Models.Participant
import dk.group11.rolesystem.Services.CourseService
import dk.group11.rolesystem.Services.ParticipantService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/courses")
class ParticipantController(private val participantService: ParticipantService, private val courseService: CourseService) {

    @GetMapping("/participant/{participantId}")
    fun getParticipant(@PathVariable participantId: Long): Participant {
        return participantService.findParticipantById(participantId)
        }

    @GetMapping("/participant")
    fun getParticipants(): MutableIterable<Participant>? {
        return participantService.getAll()
    }

    @GetMapping("/{courseId}/participant")
    fun getParticipantsForCourse(@PathVariable courseId: Long): MutableList<Participant> {
        return courseService.getCourseById(courseId).participant
    }

    @PostMapping("/{courseId}/participant")
    fun saveParticipant(@PathVariable courseId: Long, @RequestBody participant: Participant) {
        participantService.saveParticipant(courseId, participant)
    }

    @PutMapping("/{courseId}/participant/{participantId}")
    fun updateParticipant(@PathVariable participantId: Long, @RequestBody participant: Participant) {
        participantService.updateParticipant(participantId, participant)
    }

}