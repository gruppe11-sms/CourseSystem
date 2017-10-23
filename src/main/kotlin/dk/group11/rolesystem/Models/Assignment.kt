package dk.group11.rolesystem.Models

import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
data class Assignment  (
        var description: String = "",

        @OneToMany(mappedBy = "assignment", cascade = arrayOf(CascadeType.ALL))
        var evaluations: MutableList<Evaluation> = mutableListOf(),

        @ManyToOne
        @JsonBackReference
        var participant: Participant = Participant(),

        @ManyToOne
        @JsonBackReference
        var course: Course = Course()

) : Activity()
