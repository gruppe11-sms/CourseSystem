package dk.group11.rolesystem.Models

import org.springframework.format.annotation.DateTimeFormat
import java.util.*
import javax.persistence.*

@Entity
data class Course(
        var title: String = "",

        @DateTimeFormat(pattern = "dd/MM/yyyy")
        var startDate: Date = Date(),
        var endDate: Date = Date(),

        @OneToMany(mappedBy = "course", cascade = arrayOf(CascadeType.ALL))
        var participant: MutableList<Participant> = mutableListOf(),

        @OneToMany(mappedBy = "course", cascade = arrayOf(CascadeType.ALL))
        var lessons: MutableList<Lesson> = mutableListOf(),

        @OneToMany(mappedBy = "course", cascade = arrayOf(CascadeType.ALL))
        var assignment: MutableList<Assignment> = mutableListOf(),

        @OneToMany(mappedBy = "course", cascade = arrayOf(CascadeType.ALL))
        var courseEvaluations: MutableList<Evaluation> = mutableListOf(),

        @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long = 0)