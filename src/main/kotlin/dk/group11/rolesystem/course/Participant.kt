package dk.group11.rolesystem.course

import dk.group11.rolesystem.activity.Assignment
import dk.group11.rolesystem.role.Role
import javax.persistence.ManyToOne

import dk.group11.rolesystem.user.User
import javax.persistence.*


data class Participant(
           @ManyToOne
           var course: Course = Course(),
           @ManyToMany
           var role: Role = Role(),
           var assignment: List<Assignment> = ArrayList<Assignment>(),
           @OneToOne
           var user : User = User(),
           var userid: Long = user.id,
           @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long = 0
    )
