package dk.group11.coursesystem.controllers

import dk.group11.coursesystem.models.Participant
import dk.group11.coursesystem.services.CourseService
import dk.group11.coursesystem.services.DtoService
import dk.group11.coursesystem.services.ParticipantService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/courses")
class ParticipantController(private val participantService: ParticipantService,
                            private val courseService: CourseService,
                            private val dtoService: DtoService) {

    @GetMapping("/participants/{participantId}")
    fun getParticipant(@PathVariable participantId: Long): ParticipantDTO =
            dtoService.convert(participantService.findParticipantById(participantId))

    @GetMapping("/participants/user/{userId}")
    fun getParticipantsByUserId(@PathVariable userId: Long): List<ParticipantDTO> =
            participantService.findParticipantsByUserId(userId).map { dtoService.convert(it) }

    @GetMapping("/participants")
    fun getParticipants(): Iterable<ParticipantDTO> = participantService.getAll().map { dtoService.convert(it) }

    @GetMapping("/{courseId}/participants")
    fun getParticipantsForCourse(@PathVariable courseId: Long): Iterable<ParticipantDTO> =
            courseService.getCourseById(courseId).participants.map { dtoService.convert(it) }

    @PostMapping("/{courseId}/participants")
    fun saveParticipant(@PathVariable courseId: Long, @RequestBody participant: Participant) =
            participantService.saveParticipant(courseId, participant)

    @PutMapping("/{courseId}/participants/{participantId}")
    fun updateParticipant(@PathVariable participantId: Long, @RequestBody participant: Participant) =
            participantService.updateParticipant(participantId, participant)
}