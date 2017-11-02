package dk.group11.coursesystem.models

import javax.persistence.*

@Entity
data class Room(
        var name: String = "",
        var roomnr: Int = 0,

        @ManyToOne
        var lesson: Lesson = Lesson(),

        @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long = 0)