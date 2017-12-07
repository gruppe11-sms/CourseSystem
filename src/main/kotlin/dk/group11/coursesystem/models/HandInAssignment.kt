package dk.group11.coursesystem.models

import dk.group11.coursesystem.controllers.HandinAssignmentDTO
import javax.persistence.*


@Entity
data class HandInAssignment(

        @OneToMany(cascade = arrayOf(CascadeType.ALL))
        var handInIds: MutableList<UploadedFile> = mutableListOf(),

        @OneToMany(mappedBy = "handInAssignment",cascade = arrayOf(CascadeType.ALL))
        var evaluations: MutableList<Evaluation> = mutableListOf(),

        @ManyToOne
        var assignment: Assignment = Assignment(),

        @ManyToOne
        var participant: Participant = Participant(),

        @ElementCollection
        var fileNames: MutableList<String> = mutableListOf(),

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0
){
    fun toDTO(recursive: Boolean = true) : HandinAssignmentDTO {
        val evaluations = if(recursive){
            evaluations.map { it.toDTO() }
        } else {
            emptyList()
        }
        return HandinAssignmentDTO(
                id = id,
                handInIds = handInIds,
                evaluations = evaluations,
                fileNames = fileNames

        )
    }
}
