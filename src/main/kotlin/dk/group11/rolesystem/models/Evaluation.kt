package dk.group11.rolesystem.models

import javax.persistence.*

@Entity
data class Evaluation(
        var grade: String = "",
        var feedback: String = "",

        @ManyToOne
        var course: Course = Course(),

        @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long = 0
)
