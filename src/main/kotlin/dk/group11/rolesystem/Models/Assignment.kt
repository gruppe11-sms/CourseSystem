package dk.group11.rolesystem.Models

import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
data class Assignment  (

        override val title: String = "",

        var description: String = "",

        @ManyToOne
        var participant: Participant = Participant(),

        @ManyToOne
        var course: Course = Course()
) : Activity()
