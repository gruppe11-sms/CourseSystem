package dk.group11.rolesystem.Models

import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.*

@Entity
data class Room(
        var name: String = "",
        var roomnr: Int = 0,

        @ManyToOne
        @JsonBackReference
        var lesson: Lesson = Lesson(),

        @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long = 0)