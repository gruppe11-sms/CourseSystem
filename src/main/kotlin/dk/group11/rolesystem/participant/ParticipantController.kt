package dk.group11.rolesystem.participant

import dk.group11.rolesystem.course.Course
import org.springframework.web.bind.annotation.*

@RestController
class ParticipantController(val participantService: ParticipantService) {

    @GetMapping("/api/courses/{courseId}/participants/{participantId}")
    fun getParticipant(@PathVariable courseId: Long, @PathVariable participantId: Long) {
        //return participantService.getParticipants(courseId)
    }

    @GetMapping("/api/participants/{course_Id}")
    fun getCourseById(@PathVariable course_Id: Long): Course {
        return participantService.getCourseById(course_Id)
    }

    @PostMapping("/api/courses/{id}/participant")
    fun saveParticipant(@PathVariable id: Long, @RequestBody participant: Participant) {
        participantService.saveParticipant(id, participant)
    }
}