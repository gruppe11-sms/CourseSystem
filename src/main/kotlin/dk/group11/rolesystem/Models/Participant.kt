package dk.group11.rolesystem.Models

import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.*

@Entity
data class Participant(
        @ManyToOne
        //@JoinColumn(name = "course_id")
        @JsonBackReference
        var course: Course = Course(),
        var userId: Long = 0,

        @OneToMany(mappedBy = "participant", cascade = arrayOf(CascadeType.ALL))
        var assignments: MutableList<Assignment> = mutableListOf(),

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "participant_id")
        var id: Long = 0
)