package dk.group11.rolesystem.Models

import javax.persistence.Entity

@Entity
class Event(override val title: String = "") : Activity() {
    val description: String = ""
    //insert file here

}