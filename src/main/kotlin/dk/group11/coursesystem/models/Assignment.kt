package dk.group11.coursesystem.models

import dk.group11.coursesystem.controllers.AssignmentDTO
import javax.persistence.*

@Entity
data class Assignment(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0,
        var activityId: Long = 0,

        @Transient
        var activity: Activity = Activity(),

                      var description: String = "",
                      @ManyToMany(cascade = arrayOf(CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH))
                      @JoinTable(name = "assignment_participants",
                              joinColumns = arrayOf(JoinColumn(name = "assignment_id")),
                              inverseJoinColumns = arrayOf(JoinColumn(name = "participant_id"))
                      )
                      var participants: MutableSet<Participant> = mutableSetOf(),

                      @OneToMany(cascade = arrayOf(CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH))
                      @JoinColumn
                      var handInAssignments: MutableList<HandInAssignment> = mutableListOf(),

                      @ManyToOne(cascade = arrayOf(CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH))
                      @JoinColumn
                      var course: Course = Course()
) {
    fun toDTO(recursive: Boolean = true): AssignmentDTO {
        val participants = if (recursive)
            participants.map { it.toDTO(false) }
        else emptyList()
        val handInAssignments = if (recursive)
            handInAssignments.map { it.toDTO(false) }
        else
            emptyList()

        return AssignmentDTO(
                id = id,
                description = description,
                participants = participants,
                title = activity.title,
                startDate = activity.startDate,
                endDate = activity.endDate,
                handinAssignments = handInAssignments
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Assignment

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
