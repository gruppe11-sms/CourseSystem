package dk.group11.rolesystem.course

import dk.group11.rolesystem.activity.Assignment
import dk.group11.rolesystem.role.Role
import javax.persistence.*


data class Participant(@ManyToOne
                       var course: Course = Course(),
                       @ManyToMany
                       var role: Role = Role(),
                       var assignment: List<Assignment> = ArrayList<Assignment>(),
                       var userid: Long,
                       @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long = 0
)
