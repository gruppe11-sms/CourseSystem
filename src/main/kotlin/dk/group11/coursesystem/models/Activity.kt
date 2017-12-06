package dk.group11.coursesystem.models

import java.util.*

data class Activity(
        var id: Long = 0,
        var title: String = "",
        var startDate: Date = Date(),
        var endDate: Date = Date(),

        var participants: MutableSet<ActivityParticipant> = mutableSetOf()
)

data class ActivityParticipant(
        var userId: Long = 0
)