package dk.group11.rolesystem.course

import dk.group11.rolesystem.activity.Assignment
import javax.persistence.*

@Entity
data class Participant(
        //@ManyToOne
        //var course: Course = Course(),
        //var courseTitle : String = "",
        //var courseId : Long = 0,
        //@ManyToMany
        //var role: Role = Role(),
        @OneToMany(cascade = arrayOf(CascadeType.ALL))
           var assignment: List<Assignment> = ArrayList<Assignment>(),
        var userid: Long = 0,
        @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long = 0
    )
