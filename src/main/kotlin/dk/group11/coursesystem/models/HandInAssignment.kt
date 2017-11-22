package dk.group11.coursesystem.models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
data class HandInAssignment(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0,
        var handInIds: MutableList<Long> = mutableListOf(),
        var evaluations: MutableList<Evaluation> = mutableListOf(),
        var assignmentId: Long
)