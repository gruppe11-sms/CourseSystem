package dk.group11.coursesystem.models

import dk.group11.coursesystem.controllers.LessonDTO
import dk.group11.coursesystem.controllers.RoomDTO
import javax.persistence.*

@Entity
data class Room(
        var name: String = "",
        var roomNr: Int = 0,
        @ManyToOne
        var lesson: Lesson = Lesson(),
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0
) {
    fun toDTO(recursive: Boolean = true): RoomDTO {
        val lesson = if (recursive)
            lesson.toDTO(false)
        else LessonDTO()
        return RoomDTO(
                name = name,
                roomNr = roomNr,
                lesson = lesson,
                id = id
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Room

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }


}