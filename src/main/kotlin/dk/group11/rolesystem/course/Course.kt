package dk.group11.rolesystem.course

import org.springframework.format.annotation.DateTimeFormat
import java.util.*
import javax.persistence.*

@Entity
data class Course(
        var title: String = "",
        @DateTimeFormat(pattern = "dd/MM/yyyy")
          var startDate: Date = Date(),
        var endDate: Date = Date(),
        //@OneToMany
        //var Lessons: List<Lesson> = ArrayList<Lesson>(),
        @OneToMany(cascade = arrayOf(CascadeType.ALL))
        //@Cascade
        var participant: MutableList<Participant> = mutableListOf(),
        //@OneToMany
        //var evaluations: List<Evaluation> = ArrayList<Evaluation>(),
        //@OneToMany
        //var assignments: List<Assignment> = ArrayList<Assignment>(),
        @Id @GeneratedValue(strategy = GenerationType.AUTO) var courseId: Long = 0)