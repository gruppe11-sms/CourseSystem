package dk.group11.coursesystem.models

import java.util.*

data class AssembledLesson(var activityId: Long = 0,
                           val id: Long = 0,
                           val title: String = "",
                           val description: String = "",
                           var course: Course = Course(),
                           val rooms: MutableList<Room> = mutableListOf(),
                           val startDate: Date = Date(),
                           val endDate: Date = Date()) {

    constructor(lesson: Lesson, activity: Activity) : this(
            activityId = lesson.activityId,
            id = lesson.id,
            title = activity.title,
            course = lesson.course,
            startDate = activity.startDate,
            endDate = activity.endDate,
            description = lesson.description,
            rooms = lesson.rooms
    ) {
        if (lesson.activityId != activity.id)
            throw IllegalArgumentException("Lesson Id and Activity Id must match")
    }

    fun getLesson(): Lesson = Lesson(
            id = id,
            rooms = rooms,
            activityId = activityId,
            description = description,
            course = course
    )

    fun getActivity(): Activity = Activity(
            id = activityId,
            title = title,
            endDate = endDate,
            startDate = startDate
    )
}