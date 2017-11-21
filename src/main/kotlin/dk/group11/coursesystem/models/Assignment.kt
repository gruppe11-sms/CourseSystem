package dk.group11.coursesystem.models

import javax.persistence.*

@Entity
data class Assignment(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0,

        var activityId: Long = 0,

        var description: String = "",

        @ManyToMany(mappedBy = "assignments", cascade = arrayOf(CascadeType.ALL))
        var participants: MutableList<Participant> = mutableListOf(),

        @ManyToOne(cascade = arrayOf(CascadeType.ALL))
        @JoinColumn
        var course: Course = Course()

)
