package dk.group11.rolesystem.activity

import dk.group11.rolesystem.course.Course
import javax.persistence.ManyToOne

class Assignment : Activity() {
    val description: String = ""
    @ManyToOne
    val course: Course = Course()
    //insert file
}