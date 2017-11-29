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
        var rooms: MutableList<Room> = mutableListOf()
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
}