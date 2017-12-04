package dk.group11.coursesystem.models

import dk.group11.coursesystem.controllers.EvaluationDTO
import javax.persistence.*

@Entity
data class Evaluation(
        var grade: String = "",
        var feedback: String = "",
        @ManyToOne
        var course: Course = Course(),
        @ManyToOne
        var handInAssignment: HandInAssignment = HandInAssignment(),
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0
) {
    fun toDTO(): EvaluationDTO {
        return EvaluationDTO(
                id = id,
                grade = grade,
                feedback = feedback
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Evaluation

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }


}
