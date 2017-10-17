package dk.group11.rolesystem.participant

import com.fasterxml.jackson.annotation.JsonBackReference
import dk.group11.rolesystem.course.Course
import javax.persistence.*

@Entity
data class Participant(
        @ManyToOne
        @JoinColumn(name = "course_id")
        @JsonBackReference
        var course: Course = Course(),
        var userId: Long = 0,
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "participant_id")
        var id: Long = 0
)