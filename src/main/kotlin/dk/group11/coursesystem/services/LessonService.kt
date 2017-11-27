package dk.group11.coursesystem.services

import dk.group11.coursesystem.clients.*
import dk.group11.coursesystem.controllers.CourseDTO
import dk.group11.coursesystem.controllers.RoomDTO
import dk.group11.coursesystem.controllers.SimpleLessonDTO
import dk.group11.coursesystem.exceptions.BadRequestException
import dk.group11.coursesystem.models.AssembledLesson
import dk.group11.coursesystem.models.Lesson
import dk.group11.coursesystem.repositories.CourseRepository
import dk.group11.coursesystem.repositories.LessonRepository
import dk.group11.coursesystem.repositories.ParticipantRepository
import org.springframework.stereotype.Service

fun Lesson.toAuditEntryDTO(): SimpleLessonDTO {
    return SimpleLessonDTO(
            id = id,
            description = description,
            activityId = activityId,
            course = CourseDTO(id = id),
            rooms = rooms.map { RoomDTO(id = it.id) }
    )
}

@Service
class LessonService(private val lessonRepository: LessonRepository,
                    private val courseRepository: CourseRepository,
                    private val auditClient: AuditClient,
                    private val participantRepository: ParticipantRepository,
                    private val calendarClient: CalendarClient) {

    fun createLesson(lesson: AssembledLesson, courseId: Long): AssembledLesson {
        val course = courseRepository.findOne(courseId)
        lesson.course = course

        val activity = calendarClient.createActivity(lesson.getActivity())
        lesson.activityId = activity.id
        val newLesson: Lesson = lessonRepository.save(lesson.getLesson())

        auditClient.createEntry(
                action = "[CourseSystem] Create Lesson",
                data = LessonAuditEntryCreation(newLesson.toAuditEntryDTO(), courseId)
        )
        return lesson
    }

    fun getLessonsByUserId(userId: Long): List<Lesson> {
        val participants = participantRepository.findByUserId(userId)
        return participants.map { it.course }.flatMap { it.lessons }
    }

    fun getLesson(lessonId: Long): AssembledLesson {
        val lesson = lessonRepository.findOne(lessonId)
        auditClient.createEntry("[CourseSystem] Get Lesson", lessonId)
        return assemble(lesson)
    }

    fun updateLesson(lesson: AssembledLesson, lessonId: Long): AssembledLesson {
        if (lesson.id != lessonId) {
            throw BadRequestException("Lesson id attempted update does not match lessonId URL")
        }
        val before = lessonRepository.findOne(lesson.id)
        val after = lessonRepository.save(lesson.getLesson())
        calendarClient.updateActivity(lesson.getActivity())


        auditClient.createEntry(
                action = "[CourseSystem] Update Lesson",
                data = LessonAuditEntryUpdate(before = before.toAuditEntryDTO(), after = after.toAuditEntryDTO())
        )
        return lesson
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

    fun getLessons(courseId: Long): List<AssembledLesson> {
        val lessons = courseRepository.findOne(courseId).lessons
        auditClient.createEntry(action = "[CourseSystem] Get Lessons for Course", data = courseId)
        return lessons.map { assemble(it) }
    }

    fun assemble(lesson: Lesson): AssembledLesson =
            AssembledLesson(lesson, calendarClient.getActivity(lesson.activityId))
}