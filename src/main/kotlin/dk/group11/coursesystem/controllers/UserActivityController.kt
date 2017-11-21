package dk.group11.coursesystem.controllers

import dk.group11.coursesystem.services.AssignmentService
import dk.group11.coursesystem.services.LessonService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping ("/api/users/{id}/")
class UserActivityController(
        private val lessonService: LessonService,
        private val assignmentService: AssignmentService) {

    fun getLessonsForParticipants(@PathVariable id: Long): List<LessonDTO>{
        return lessonService.getLessonsByUserId(id).map { it.toDTO() }
    }

    fun getAssignmentsForParticpants(@PathVariable id: Long) : List<AssignmentDTO>{
        return assignmentService.getAssigmentsByUserId(id).map { it.toDTO() }
    }
}