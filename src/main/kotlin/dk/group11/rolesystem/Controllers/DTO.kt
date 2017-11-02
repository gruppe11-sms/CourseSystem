package dk.group11.rolesystem.Controllers

import dk.group11.rolesystem.Models.*
import java.util.*

fun Course.toDTO(recursive: Boolean = true): CourseDTO {
    //needs refactoring to participants as it is a list should happen in the course model
        val participant = if (recursive)
            participant.map { it.toDTO(recursive) }
        else emptyList()

        val lessons = if (recursive)
            lessons.map { it.toDTO() }
        else emptyList()

        val assignment = if (recursive)
            assignment.map { it.toDTO() }
        else emptyList()

    return CourseDTO(
            0,
            "",
            Date(),
            Date(),
            emptyList(),
            emptyList(),
            emptyList(),
            emptyList()
    )

}

fun Participant.toDTO(recursive: Boolean = true): ParticipantDTO {
    return ParticipantDTO(
            0,
            0,
            Course(),
            emptyList()
    );
}

fun Lesson.toDTO(recursive: Boolean = true): LessonDTO {
    return LessonDTO(
            Course(),
            "",
            emptyList()
    )
}

fun Assignment.toDTO(recursive: Boolean = true): AssignmentDTO {
    return AssignmentDTO(
            "",
            "",
            Participant(),
            Course()
    )
}

data class CourseDTO(
        val id: Long,
        val title: String,
        val startDate: Date = Date(),
        val endDate: Date = Date(),
        val participant: List<ParticipantDTO> = emptyList(),
        val lessons: List<LessonDTO> = emptyList(),
        val assignment: List<AssignmentDTO> = emptyList(),
        val courseEvaluations: List<EvaluationDTO> = emptyList()
)
data class ParticipantDTO(
        val id: Long,
        val userId: Long,
        val course: Course,
        val assignments: List<Assignment> = emptyList()
        )

data class EvaluationDTO (
        val id: Long,
        val grade: String,
        val feedback: String,
        val course: Course
)

data class LessonDTO(
    val course: Course,
    val title: String,
    val rooms: List<Room> = emptyList()
)

data class AssignmentDTO (
        val title: String,
        val description: String,
        val participant: Participant,
        val course: Course
)

