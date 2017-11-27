package dk.group11.coursesystem.models

import java.util.*

data class AssembledAssignment(val id: Long = 0,
                               var activityId: Long = 0,
                               val description: String = "",
                               val participants: MutableList<Participant> = mutableListOf(),
                               var course: Course = Course(),
                               val title: String = "",
                               val startDate: Date = Date(),
                               val endDate: Date = Date()) {

    constructor(assignment: Assignment, activity: Activity) : this(
            id = assignment.id,
            activityId = assignment.activityId,
            startDate = activity.startDate,
            endDate = activity.endDate,
            title = activity.title,
            course = assignment.course,
            description = assignment.description,
            participants = assignment.participants
    )

    fun getAssignment(): Assignment = Assignment(
            id = id,
            activityId = activityId,
            description = description,
            course = course,
            participants = participants
    )

    fun getActivity(): Activity = Activity(
            id = activityId,
            title = title,
            endDate = endDate,
            startDate = startDate
    )
}