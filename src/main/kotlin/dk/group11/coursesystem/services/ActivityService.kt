package dk.group11.coursesystem.services

import dk.group11.coursesystem.clients.CalendarClient
import dk.group11.coursesystem.models.*
import org.springframework.stereotype.Service

@Service
class ActivityService(
        private val calenderClient: CalendarClient
) {
    fun createActivity(assignment: Assignment): Activity {

        val activity = Activity(
                title = assignment.activity.title,
                endDate = assignment.activity.endDate,
                startDate = assignment.activity.startDate,
                participants = assignment.participants.map { ActivityParticipant(it.userId) }.toMutableSet()
        )

        return calenderClient.createActivity(activity)
    }

    fun createActivity(lesson: Lesson, participants: Iterable<Participant>): Activity {

        val activity = Activity(
                title = lesson.activity.title,
                endDate = lesson.activity.endDate,
                startDate = lesson.activity.startDate,
                participants = participants.map { ActivityParticipant(it.userId) }.toMutableSet()
        )

        return calenderClient.createActivity(activity)
    }

    fun updateActivity(assignment: Assignment): Activity {
        return calenderClient.updateActivity(assignment.activity)
    }

    fun updateActivity(lesson: Lesson): Activity {
        return calenderClient.updateActivity(lesson.activity)
    }

    fun getActivity(id: Long): Activity {
        return calenderClient.getActivity(id).first()
    }

    fun getActivities(id: Collection<Long>): Collection<Activity> {
        return calenderClient.getActivity(*id.toLongArray())
    }

    fun deleteActivity(activityId: Long) {
        calenderClient.deleteActivity(activityId)
    }

}