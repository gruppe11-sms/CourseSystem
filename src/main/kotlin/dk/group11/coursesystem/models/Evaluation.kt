package dk.group11.coursesystem.models

import dk.group11.coursesystem.controllers.EvaluationDTO
import javax.persistence.*

@Entity
data class Evaluation(
        var grade: String = "",
        var feedback: String = "",
        @ManyToOne
        var course: Course = Course(),
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
}
