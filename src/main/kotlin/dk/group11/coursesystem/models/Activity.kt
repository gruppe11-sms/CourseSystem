package dk.group11.coursesystem.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class Activity(
        var id: Long = 0,
        var title: String = "",
        var startDate: Date = Date(),
        var endDate: Date = Date(),

        var participants: MutableSet<ActivityParticipant> = mutableSetOf()
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ActivityParticipant(
        var userId: Long = 0
)