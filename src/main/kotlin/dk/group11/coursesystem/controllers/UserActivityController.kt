package dk.group11.coursesystem.controllers

import dk.group11.coursesystem.services.AssignmentService
import dk.group11.coursesystem.services.DtoService
import dk.group11.coursesystem.services.LessonService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users/{id}")
class UserActivityController(private val lessonService: LessonService,
                             private val assignmentService: AssignmentService,
                             private val dtoService: DtoService) {

    @GetMapping("/lessons")
    fun getLessonsForParticipants(@PathVariable id: Long): List<SimpleLessonDTO> =
            lessonService.getLessonsByUserId(id).map { dtoService.convert(it) }

    @GetMapping("/assignments")
    fun getAssignmentsForParticipants(@PathVariable id: Long): List<SimpleAssignmentDTO> =
            assignmentService.getAssignmentsByUserId(id).map { dtoService.convert(it) }
}