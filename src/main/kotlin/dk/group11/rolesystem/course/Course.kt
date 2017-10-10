package dk.group11.rolesystem.course

import dk.group11.rolesystem.evaluation.Evaluation
import dk.group11.rolesystem.lesson.Lesson
import dk.group11.rolesystem.activity.Assignment
import org.springframework.format.annotation.DateTimeFormat
import java.util.*
import javax.persistence.*
import kotlin.collections.ArrayList


data class Course(
          var title: String = "",
          @DateTimeFormat(pattern = "dd/MM/yyyy")
          var startDate: Date = Date(),
          var endDate: Date = Date(),
          @OneToMany
          var Lessons: List<Lesson> = ArrayList<Lesson>(),
          @ManyToMany
          var participant: List<Participant> = ArrayList<Participant>(),
          @OneToMany
          var evaluations: List<Evaluation> = ArrayList<Evaluation>(),
          @OneToMany
          var assignments: List<Assignment> = ArrayList<Assignment>(),
          @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long = 0) {


}