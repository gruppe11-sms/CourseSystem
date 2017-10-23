package dk.group11.rolesystem.Models

import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.*

@Entity
data class Assignment  (
        var description: String = "",

        @OneToMany(cascade = arrayOf(CascadeType.ALL))
        var evaluations: MutableList<Evaluation> = mutableListOf(),

        @ManyToOne
        @JoinColumn(name = "course_id")
        @JsonBackReference
        var course: Course = Course()

) : Activity()
