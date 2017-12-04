package dk.group11.coursesystem.models

import dk.group11.coursesystem.controllers.LessonDTO
import javax.persistence.*

@Entity
data class Lesson(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0,
        var activityId: Long = 0,

        @Transient
        var activity: Activity = Activity(),

        var description: String = "",
        @ManyToOne
        var course: Course = Course(),
        @OneToMany(mappedBy = "lesson", cascade = arrayOf(CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH))
        var rooms: MutableSet<Room> = mutableSetOf()
) {
    fun toDTO(recursive: Boolean = true): LessonDTO {
        val rooms = if (recursive)
            rooms.map { it.toDTO(false) }
        else emptyList()
        return LessonDTO(
                id = id,
                rooms = rooms,
                title = activity.title,
                endDate = activity.endDate,
                startDate = activity.startDate,
                description = description
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Lesson

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }


}