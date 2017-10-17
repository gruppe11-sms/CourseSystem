package dk.group11.rolesystem.course

import dk.group11.rolesystem.participant.Participant
import org.springframework.format.annotation.DateTimeFormat
import java.util.*
import javax.persistence.*

@Entity
data class Course(
        var title: String = "",

        @DateTimeFormat(pattern = "dd/MM/yyyy")
        var startDate: Date = Date(),
        var endDate: Date = Date(),

        @OneToMany(mappedBy = "course", cascade = arrayOf(CascadeType.ALL))
        var participant: MutableList<Participant> = mutableListOf(),

        @Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "course_id") var id: Long = 0)