package dk.group11.coursesystem.models

import javax.persistence.Entity

@Entity
class Event(override val title: String = "") : Activity() {
    val description: String = ""
    //insert file here

}