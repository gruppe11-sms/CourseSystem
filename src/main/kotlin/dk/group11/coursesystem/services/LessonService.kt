package dk.group11.coursesystem.services

import dk.group11.coursesystem.clients.*
import dk.group11.coursesystem.controllers.CourseDTO
import dk.group11.coursesystem.controllers.LessonDTO
import dk.group11.coursesystem.controllers.RoomDTO
import dk.group11.coursesystem.models.Activity
import dk.group11.coursesystem.models.Lesson
import dk.group11.coursesystem.repositories.CourseRepository
import dk.group11.coursesystem.repositories.LessonRepository
import dk.group11.coursesystem.repositories.ParticipantRepository
import dk.group11.coursesystem.repositories.RoomRepository
import org.springframework.stereotype.Service

fun Lesson.toAuditEntryDTO(): LessonDTO {
    return LessonDTO(
            id = id,
            description = description,
            startDate = activity.startDate,
            endDate = activity.endDate,
            title = activity.title,
            course = CourseDTO(id = id),
            rooms = rooms.map { RoomDTO(id = it.id) }
    )
}

@Service
class LessonService(private val lessonRepository: LessonRepository,
                    private val courseRepository: CourseRepository,
                    private val auditClient: AuditClient,
                    private val participantRepository: ParticipantRepository,
                    private val calendarClient: CalendarClient,
                    private val roomRepository: RoomRepository) {

    private fun createLesson(lesson: Lesson, courseId: Long): Lesson {
        val course = courseRepository.findOne(courseId)
        lesson.course = course

        val newLesson: Lesson = lessonRepository.save(lesson)

        auditClient.createEntry(
                action = "[CourseSystem] Create Lesson",
                data = LessonAuditEntryCreation(newLesson.toAuditEntryDTO(), courseId)
        )
        return lesson
    }

    fun createLesson(lessonDTO: LessonDTO, courseId: Long): Lesson {

        println(lessonDTO)

        val activity = calendarClient.createActivity(Activity(title = lessonDTO.title, startDate = lessonDTO.startDate, endDate = lessonDTO.endDate))

        val lesson = Lesson(activityId = activity.id, activity = activity, description = lessonDTO.description)

        return createLesson(lesson, courseId)
    }

    fun getLesson(lessonId: Long): Lesson {
        auditClient.createEntry("[CourseSystem] Get Lesson", lessonId)
        val lesson = lessonRepository.findOne(lessonId)
        lesson.activity = calendarClient.getActivity(lesson.activityId)
        return lesson
    }

    fun updateLesson(lesson: LessonDTO): Lesson {
        val before = lessonRepository.findOne(lesson.id)

        auditClient.createEntry(
                action = "[CourseSystem] Update Lesson",
                data = LessonAuditEntryUpdate(before = before.toAuditEntryDTO(), after = lesson)
        )

        before.description = lesson.description
        before.rooms = lesson.rooms.map { roomRepository.findOne(it.id) }.toMutableSet()

        calendarClient.updateActivity(Activity(before.activityId, lesson.title, lesson.startDate, lesson.endDate))

        return before
    }

    fun deleteLesson(lessonId: Long) {
        val deleted = lessonRepository.findOne(lessonId)
        calendarClient.deleteActivity(deleted.activityId)
        lessonRepository.delete(lessonId)
        auditClient.createEntry(
                action = "[CourseSystem] Delete Lesson",
                data = LessonAuditEntryDelete(deleted.toAuditEntryDTO(), lessonId)
        )
    }

    fun getLessons(courseId: Long): Set<Lesson> {
        val lessons = courseRepository.findOne(courseId).lessons
        auditClient.createEntry(action = "[CourseSystem] Get Lessons for Course", data = courseId)
        return lessons
    }

}