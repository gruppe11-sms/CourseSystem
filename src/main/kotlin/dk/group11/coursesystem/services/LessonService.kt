package dk.group11.coursesystem.services

import dk.group11.coursesystem.auditClient.AuditClient
import dk.group11.coursesystem.controllers.LessonDTO
import dk.group11.coursesystem.controllers.toDTO
import dk.group11.coursesystem.models.Lesson
import dk.group11.coursesystem.repositories.CourseRepository
import dk.group11.coursesystem.repositories.LessonRepository
import dk.group11.coursesystem.security.SecurityService
import org.springframework.stereotype.Service

data class createLessonAuditEntry(val lesson: LessonDTO, val courseId: Long)
data class updateLessonAuditEntry(val before: LessonDTO, val after: Lesson)
data class deleteLessonAuditEntry(val deleted: LessonDTO, val lessonId: Long)
@Service
class LessonService(
        val lessonRepository: LessonRepository,
        val courseRepository: CourseRepository,
        val auditClient: AuditClient,
        val securityService: SecurityService) {

    fun createLesson(lesson: Lesson, courseId: Long): Lesson {
        val course = courseRepository.findOne(courseId)
        lesson.course = course
        lessonRepository.save(lesson)
        auditClient.createEntry("[CourseSystem] Create Lesson", createLessonAuditEntry(lesson.toDTO(true), courseId), securityService.getToken())
        return lesson

    }

    fun getLesson(lessonId: Long): Lesson {
        auditClient.createEntry("[CourseSystem] Get Lesson", lessonId, securityService.getToken())
        return lessonRepository.findOne(lessonId)
    }

    fun updateLesson(lesson: Lesson): Lesson {
        val current = lessonRepository.findOne(lesson.id)
        auditClient.createEntry("[CourseSystem] Update Lesson", updateLessonAuditEntry(current.toDTO(false), lesson), securityService.getToken())
        return lessonRepository.save(lesson)

    }

    fun deleteLesson(lessonId: Long) {
        val deleted = lessonRepository.findOne(lessonId)
        auditClient.createEntry("[CourseSystem] Delete Lesson", deleteLessonAuditEntry(deleted.toDTO(false), lessonId), securityService.getToken())
        lessonRepository.delete(lessonId)
    }

    fun getLessons(courseId: Long): List<Lesson> {
        auditClient.createEntry("[CourseSystem] Get Lessons for Course", courseId, securityService.getToken())
        return courseRepository.findOne(courseId).lessons
    }


}