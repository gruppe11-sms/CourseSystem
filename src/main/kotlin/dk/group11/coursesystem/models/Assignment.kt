package dk.group11.coursesystem.models

import javax.persistence.*

@Entity
data class Assignment  (

        override val title: String = "",

        var description: String = "",

        @ManyToMany(mappedBy = "assignments", cascade = arrayOf(CascadeType.ALL))
        var participants: MutableList<Participant> = mutableListOf(),

        @ManyToOne(cascade = arrayOf(CascadeType.ALL))
        @JoinColumn
        var course: Course = Course()

) : Activity()
