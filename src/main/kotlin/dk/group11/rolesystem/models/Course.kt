package dk.group11.rolesystem.models

import org.springframework.format.annotation.DateTimeFormat
import java.util.*
import javax.persistence.*

@Entity
data class Course(
        var title: String = "",

        var description: String = "",

        @DateTimeFormat(pattern = "dd/MM/yyyy")
        var startDate: Date = Date(),
        var endDate: Date = Date(),

        @OneToMany(mappedBy = "course", cascade = arrayOf(CascadeType.ALL))
        var participants: MutableList<Participant> = mutableListOf(),

        @OneToMany(mappedBy = "course", cascade = arrayOf(CascadeType.ALL))
        var lessons: MutableList<Lesson> = mutableListOf(),

        @OneToMany(mappedBy = "course", cascade = arrayOf(CascadeType.ALL))
        var assignments: MutableList<Assignment> = mutableListOf(),

        @OneToMany(mappedBy = "course", cascade = arrayOf(CascadeType.ALL))
        var evaluations: MutableList<Evaluation> = mutableListOf(),

        @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long = 0)