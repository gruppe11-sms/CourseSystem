package dk.group11.coursesystem.models

import dk.group11.coursesystem.controllers.CourseDTO
import java.util.*
import javax.persistence.*

@Entity
data class Course(
        var title: String = "",
        var description: String = "",
        var startDate: Date = Date(),
        var endDate: Date = Date(),
        @OneToMany(mappedBy = "course", cascade = arrayOf(CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH))
        var participants: MutableSet<Participant> = mutableSetOf(),
        @OneToMany(mappedBy = "course", cascade = arrayOf(CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH))
        var lessons: MutableSet<Lesson> = mutableSetOf(),
        @OneToMany(mappedBy = "course", cascade = arrayOf(CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH))
        var assignments: MutableSet<Assignment> = mutableSetOf(),
        @OneToMany(mappedBy = "course", cascade = arrayOf(CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH))
        var evaluations: MutableSet<Evaluation> = mutableSetOf(),
        @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long = 0
) {

    fun toDTO(recursive: Boolean = true): CourseDTO {
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
            evaluations.map { it.toDTO() } else emptyList()
        return CourseDTO(
                id = id,
                title = title,
                startDate = startDate,
                endDate = endDate,
                participants = participants,
                lessons = lessons,
                assignments = assignments,
                evaluations = evaluations,
                description = description
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Course

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }


}