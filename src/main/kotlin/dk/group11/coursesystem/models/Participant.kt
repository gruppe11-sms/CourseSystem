package dk.group11.coursesystem.models

import javax.persistence.*

@Entity
data class Participant(
        var userId: Long = 0,

        @ManyToOne
        var course: Course = Course(),

        @ManyToMany(cascade = arrayOf(CascadeType.ALL))
        @JoinColumn
        var assignments: MutableList<Assignment> = mutableListOf(),

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0,

        @OneToMany(mappedBy = "participant", cascade = arrayOf(CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH))
        var handInAssignments: MutableList<HandInAssignment> = mutableListOf()
)