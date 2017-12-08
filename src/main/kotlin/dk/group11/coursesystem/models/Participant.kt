package dk.group11.coursesystem.models

import dk.group11.coursesystem.controllers.ParticipantDTO
import javax.persistence.*

@Entity
data class Participant(
        var userId: Long = 0,
        @ManyToOne
        var course: Course = Course(),
        @ManyToMany(cascade = arrayOf(CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH))
        @JoinTable(name = "assignment_participants",
                joinColumns = arrayOf(JoinColumn(name = "participant_id")),
                inverseJoinColumns = arrayOf(JoinColumn(name = "assignment_id"))
        )
        var assignments: MutableList<Assignment> = mutableListOf(),

        @OneToMany(mappedBy = "participant", cascade = arrayOf(CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH))
        var handInAssignments: MutableList<HandInAssignment> = mutableListOf(),

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0
) {
    fun toDTO(recursive: Boolean = true): ParticipantDTO {
        val assignments = if (recursive)
            assignments.map { it.toDTO(false) }
        else emptyList()
        return ParticipantDTO(
                id = id,
                userId = userId,
                assignments = assignments
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Participant

        if (userId != other.userId) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userId.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }


}