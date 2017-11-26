package dk.group11.coursesystem.controllers

import java.util.*

data class SimpleEventDTO(
        val id: Long = 0,
        val description: String = "",
        val activityId: Long = 0
)


data class CourseDTO(
        val id: Long = 0,
        val title: String = "",
        val description: String = "",
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

data class EvaluationDTO(
        val id: Long = 0,
        val grade: String = "",
        val feedback: String = ""
)

data class LessonDTO(
        val id: Long = 0,
        val rooms: List<RoomDTO> = emptyList(),
        val title: String = "",
        val startDate: Date = Date(),
        val endDate: Date = Date(),
        val description: String = ""
)

data class AssignmentDTO(
        val id: Long = 0,
        val description: String = "",
        val participants: List<ParticipantDTO> = emptyList(),
        val title: String = "",
        val startDate: Date = Date(),
        val endDate: Date = Date()
)

data class RoomDTO(
        val name: String = "",
        val roomNr: Int = 0,
        val lesson: LessonDTO = LessonDTO(),
        val id: Long = 0
)

data class EventDTO(
        val id: Long = 0,
        val description: String = "",
        val title: String = "",
        val startDate: Date = Date(),
        val endDate: Date = Date()
)

data class SimpleLessonDTO(
        val id: Long = 0,
        val activityId: Long = 0,
        val description: String = "",
        val course: CourseDTO = CourseDTO(),
        val rooms: List<RoomDTO> = emptyList()
)

data class SimpleAssignmentDTO(
        val id: Long = 0,
        val description: String = "",
        val participants: List<ParticipantDTO> = emptyList()
)
