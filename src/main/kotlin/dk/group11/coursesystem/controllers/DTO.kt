package dk.group11.coursesystem.controllers

import dk.group11.coursesystem.models.*
import java.util.*

fun Course.toDTO(recursive: Boolean = true): CourseDTO {
    //needs refactoring to participants as it is a list should happen in the course model
        val participants = if (recursive)
            participants.map { it.toDTO(false) }
        else emptyList()

        val lessons = if (recursive)
            lessons.map { it.toDTO(false) }
        else emptyList()

        val assignments = if (recursive)
            assignments.map { it.toDTO(false) }
        else emptyList()

        val evaluations = if (recursive)
            evaluations.map { it.toDTO(false) }
        else emptyList()

    return CourseDTO(
            id = id,
            title = title,
            startDate = startDate,
            endDate = endDate,
            participants = participants,
            lessons = lessons,
            assignments = assignments,
            evaluations = evaluations
    )
}

fun Participant.toDTO(recursive: Boolean = true): ParticipantDTO {
    val assignments = if (recursive)
        assignments.map { it.toDTO(false) }
    else emptyList()

    return ParticipantDTO(
            id = id,
            userId = userId,
            assignments = assignments
    )
}

fun Lesson.toDTO(recursive: Boolean = true): LessonDTO {
    val rooms = if (recursive)
        room.map { it.toDTO() }
    else emptyList()

    return LessonDTO(
            title = title,
            rooms = rooms
    )
}

fun Assignment.toDTO(recursive: Boolean = true): AssignmentDTO {

    val participants = if (recursive)
        participants.map { it.toDTO(false) }
    else emptyList()

    return AssignmentDTO(
            title = title,
            startdate = startdate,
            enddate = enddate,
            description = description,
            participant = participants
    )
}

fun Evaluation.toDTO(recursive: Boolean = true): EvaluationDTO {
    return EvaluationDTO(
            id = id,
            grade = grade,
            feedback = feedback
    )
}

fun Room.toDTO(recursive: Boolean = true): RoomDTO {
    val lesson = if (recursive)
        lesson.toDTO()
    else LessonDTO()

    return RoomDTO(
            name = name,
            roomNr = roomnr,
            lesson = lesson,
            id = id
    )
}

data class CourseDTO(
        val id: Long = 0,
        val title: String = "",
        val startDate: Date = Date(),
        val endDate: Date = Date(),
        val participants: List<ParticipantDTO> = emptyList(),
        val lessons: List<LessonDTO> = emptyList(),
        val assignments: List<AssignmentDTO> = emptyList(),
        val evaluations: List<EvaluationDTO> = emptyList()
)
data class ParticipantDTO(
        val id: Long = 0,
        val userId: Long = 0,
        val assignments: List<AssignmentDTO> = emptyList()
        )

data class EvaluationDTO (
        val id: Long = 0,
        val grade: String = "",
        val feedback: String = ""
)

data class LessonDTO(
        val title: String = "",
        val rooms: List<RoomDTO> = emptyList()
)

data class AssignmentDTO (
        val title: String = "",
        val description: String = "",
        val startdate: Date = Date(),
        val enddate: Date = Date(),
        val participant: List<ParticipantDTO> =  emptyList()
)

data class RoomDTO (
        val name: String = "",
        val roomNr: Int = 0,
        val lesson: LessonDTO = LessonDTO(),
        val id: Long = 0

)