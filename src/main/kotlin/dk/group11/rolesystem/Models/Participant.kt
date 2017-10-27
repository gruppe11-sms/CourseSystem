package dk.group11.rolesystem.Models

import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.*

@Entity
data class Participant(
        var userId: Long = 0,

        @ManyToOne
        @JsonBackReference
        var course: Course = Course(),
        
        @OneToMany(mappedBy = "participant", cascade = arrayOf(CascadeType.ALL))
        var assignments: MutableList<Assignment> = mutableListOf(),

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "participant_id")
        var id: Long = 0
)