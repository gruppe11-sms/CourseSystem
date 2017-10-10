package dk.group11.rolesystem.course

import dk.group11.rolesystem.evaluation.Evaluation
import dk.group11.rolesystem.lesson.Lesson
import org.springframework.format.annotation.DateTimeFormat
import java.util.*
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import kotlin.collections.ArrayList


data class Course(var title: String = "",
                  @DateTimeFormat(pattern = "dd/MM/yyyy")
                  var startDate: Date = Date(),
                  var endDate: Date = Date(),
                  var Lessons: List<Lesson> = ArrayList<Lesson>(),
                  var participant: List<Participant> = ArrayList<Participant>(),
                  var evaluations: List<Evaluation> = ArrayList<Evaluation>(),
                  @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long = 0) {

    //val title : String = ""
    //val startDate : Date = Date()
    //val endDate : Date = Date()

    //@OneToMany
    //val Lessons : List<Lesson> = ArrayList<Lesson>()

    //@OneToMany
    //val participant : List<Participant> = ArrayList<Participant>()

}