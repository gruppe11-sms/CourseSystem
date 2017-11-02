package dk.group11.rolesystem.models

import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
data class Assignment  (

        override val title: String = "",

        var description: String = "",

        @ManyToOne
        var participant: Participant = Participant(),

        @ManyToOne
        var course: Course = Course()
) : Activity()
