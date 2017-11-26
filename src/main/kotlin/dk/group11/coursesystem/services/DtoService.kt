package dk.group11.coursesystem.services

import dk.group11.coursesystem.controllers.*
import dk.group11.coursesystem.models.*
import org.springframework.stereotype.Service

@Service
class DtoService(private val assignmentService: AssignmentService,
                 private val lessonService: LessonService) {

    fun convert(course: Course, recursive: Boolean = true): CourseDTO {
        //needs refactoring to participants as it is a list should happen in the course model
        val participants = if (recursive)
            course.participants.map { convert(it, recursive = false) }
        else emptyList()
        val lessons = if (recursive)
            course.lessons.map { convert(lessonService.assemble(it), recursive = false) }
        else emptyList()
        val assignments = if (recursive)
            course.assignments.map { convert(assignmentService.assemble(it), recursive = false) }
        else emptyList()
        val evaluations = if (recursive)
            course.evaluations.map { convert(it) } else emptyList()
        return CourseDTO(
                id = course.id,
                title = course.title,
                startDate = course.startDate,
                endDate = course.endDate,
                participants = participants,
                lessons = lessons,
                assignments = assignments,
                evaluations = evaluations,
                description = course.description
        )
    }

    fun convert(participant: Participant, recursive: Boolean = true): ParticipantDTO {
        val assignments = if (recursive)
            participant.assignments.map { convert(assignmentService.assemble(it), recursive = false) }
        else emptyList()
        return ParticipantDTO(
                id = participant.id,
                userId = participant.userId,
                assignments = assignments
        )
    }

    fun convert(lesson: AssembledLesson, recursive: Boolean = true): LessonDTO {
        val rooms = if (recursive)
            lesson.rooms.map { convert(it, recursive = false) }
        else emptyList()
        return LessonDTO(
                id = lesson.id,
                rooms = rooms,
                title = lesson.title,
                endDate = lesson.endDate,
                startDate = lesson.startDate,
                description = lesson.description
        )
    }

    fun convert(assignment: AssembledAssignment, recursive: Boolean = true): AssignmentDTO {
        val participants = if (recursive)
            assignment.participants.map { convert(it, recursive = false) }
        else emptyList()
        return AssignmentDTO(
                id = assignment.id,
                description = assignment.description,
                participants = participants,
                title = assignment.title,
                startDate = assignment.startDate,
                endDate = assignment.endDate
        )
    }

    fun convert(evaluation: Evaluation): EvaluationDTO {
        return EvaluationDTO(
                id = evaluation.id,
                grade = evaluation.grade,
                feedback = evaluation.feedback
        )
    }

    fun convert(room: Room, recursive: Boolean = true): RoomDTO {
        val lesson = if (recursive)
            convert(lessonService.assemble(room.lesson), recursive = false)
        else LessonDTO()
        return RoomDTO(
                name = room.name,
                roomNr = room.roomNr,
                lesson = lesson,
                id = room.id
        )
    }

    fun convert(lesson: Lesson, recursive: Boolean = true): SimpleLessonDTO {
        val course = if (recursive) convert(lesson.course, recursive = false) else CourseDTO()
        val rooms = if (recursive) lesson.rooms.map { convert(it, recursive = false) } else emptyList()
        return SimpleLessonDTO(
                id = lesson.id,
                description = lesson.description,
                activityId = lesson.activityId,
                course = course,
                rooms = rooms
        )
    }

    fun convert(assignment: Assignment, recursive: Boolean = true): SimpleAssignmentDTO {
        val participants = if (recursive) assignment.participants.map { convert(it, recursive = false) }
        else emptyList()
        return SimpleAssignmentDTO(
                id = assignment.id,
                description = assignment.description,
                participants = participants
        )
    }

    fun convert(event: Event): SimpleEventDTO =
            SimpleEventDTO(activityId = event.activityId, description = event.description, id = event.id)

}