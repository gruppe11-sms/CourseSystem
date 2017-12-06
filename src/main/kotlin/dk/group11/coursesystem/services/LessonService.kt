package dk.group11.coursesystem.services

import dk.group11.coursesystem.clients.AuditClient
import dk.group11.coursesystem.clients.LessonAuditEntryCreation
import dk.group11.coursesystem.clients.LessonAuditEntryDelete
import dk.group11.coursesystem.clients.LessonAuditEntryUpdate
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
import javax.transaction.Transactional

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
                    private val activityService: ActivityService,
                    private val roomRepository: RoomRepository) {

    private fun createLesson(lesson: Lesson, courseId: Long): Lesson {
        val course = courseRepository.findOne(courseId)
        lesson.course = course

        lesson.activity = activityService.createActivity(lesson, course.participants)
        lesson.activityId = lesson.activityId

        val newLesson: Lesson = lessonRepository.save(lesson)

        auditClient.createEntry(
                action = "[CourseSystem] Create Lesson",
                data = LessonAuditEntryCreation(newLesson.toAuditEntryDTO(), courseId)
        )
        return lesson
    }

    fun createLesson(lessonDTO: LessonDTO, courseId: Long): Lesson {
        val lesson = Lesson(
                activity = Activity(title = lessonDTO.title, startDate = lessonDTO.startDate, endDate = lessonDTO.endDate),
                description = lessonDTO.description
        )

        return createLesson(lesson, courseId)
    }

    fun getLesson(lessonId: Long): Lesson {
        auditClient.createEntry("[CourseSystem] Get Lesson", lessonId)
        val lesson = lessonRepository.findOne(lessonId)
        lesson.activity = activityService.getActivity(lesson.activityId)
        return lesson
    }

    @Transactional
    fun updateLesson(lesson: Lesson): Lesson {

        val currentLesson = lessonRepository.findOne(lesson.id)


        auditClient.createEntry(
                action = "[CourseSystem] Update Lesson",
                data = LessonAuditEntryUpdate(before = currentLesson.toAuditEntryDTO(), after = lesson.toDTO(true))
        )

        lesson.activity.id = currentLesson.activityId

        currentLesson.description = lesson.description
        currentLesson.activity = activityService.updateActivity(lesson)

        currentLesson.rooms = lesson.rooms.map { roomRepository.findOne(it.id) }.toMutableSet()

        return currentLesson
    }

    fun updateLesson(lessonDTO: LessonDTO): Lesson {

        val lesson = Lesson(
                id = lessonDTO.id,
                activity = Activity(
                        startDate = lessonDTO.startDate,
                        endDate = lessonDTO.endDate,
                        title = lessonDTO.title
                ),
                description = lessonDTO.description
        )

        return updateLesson(lesson)
    }

    fun deleteLesson(lessonId: Long) {
        val deleted = lessonRepository.findOne(lessonId)
        activityService.deleteActivity(deleted.activityId)
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