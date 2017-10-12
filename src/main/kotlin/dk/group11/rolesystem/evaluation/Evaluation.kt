package dk.group11.rolesystem.evaluation

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Evaluation(
        var grade: String = "",
        var feedback: String = "",
        @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long = 0
)
