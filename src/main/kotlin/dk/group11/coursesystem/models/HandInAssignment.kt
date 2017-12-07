package dk.group11.coursesystem.models

import javax.persistence.*


@Entity
data class HandInAssignment(

        @OneToMany(cascade = arrayOf(CascadeType.ALL))
        var handInIds: MutableList<UploadedFile> = mutableListOf(),

        @OneToMany(mappedBy = "handInAssignment",cascade = arrayOf(CascadeType.ALL))
        var evaluations: MutableList<Evaluation> = mutableListOf(),

        var assignmentId: Long = 0,

        @ManyToOne
        var participant: Participant = Participant(),

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0
)