package dk.group11.coursesystem.services

import dk.group11.coursesystem.clients.AuditClient
import dk.group11.coursesystem.clients.CalendarClient
import dk.group11.coursesystem.clients.CourseAuditEntryDelete
import dk.group11.coursesystem.clients.CourseAuditEntryGet
import dk.group11.coursesystem.controllers.CourseDTO
import dk.group11.coursesystem.exceptions.BadRequestException
import dk.group11.coursesystem.models.Course
import dk.group11.coursesystem.models.Participant
import dk.group11.coursesystem.repositories.CourseRepository
import dk.group11.coursesystem.repositories.ParticipantRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

data class updateCourseAuditEntry(val before: CourseDTO, val after: CourseDTO)

@Service
class CourseService(private val courseRepository: CourseRepository,
                    private val auditClient: AuditClient,
                    private val calendarClient: CalendarClient,
                    private val participantRepository: ParticipantRepository) {

    fun getCourseById(courseId: Long): Course {
        auditClient.createEntry("[CourseSystem] Get Course", CourseAuditEntryGet(courseId))
        val course = courseRepository.findOne(courseId)
        if (course != null) {
            course.assignments.forEach {
                it.activity = calendarClient.getActivity(it.activityId)
            }
            course.lessons.forEach {
                it.activity = calendarClient.getActivity(it.activityId)
            }
            return course
        } else {
            throw BadRequestException("Course doesn't exist for course id '$courseId'")
        }
    }

    fun getCourses(): Iterable<Course> {
        auditClient.createEntry("[CourseSystem] Get All Courses", "")
        /* TODO The optimal solution for this implementation would be to ask the rolesystem
         * for what type of user is currently logged in, and we would have returned the
         * specific courses depending on the logged in type of user. (admin/normal user etc. */
        // return participantRepository.findByUserId(securityService.getId()).map { it.course }
        return courseRepository.findAll()
    }

    fun createCourse(course: Course): Course {
        val newCourse = courseRepository.save(course)
        auditClient.createEntry(
                action = "[CourseSystem] Create Course",
                data = newCourse.toDTO(true)
        )
        return course
    }

    @Transactional
    fun updateCourse(course: Course): Course {

        val currentCourse = courseRepository.findOne(course.id)
        auditClient.createEntry("[CourseSystem] Update Course", updateCourseAuditEntry(currentCourse.toDTO(), course.toDTO()))

        currentCourse.title = course.title
        currentCourse.description = course.description
        currentCourse.startDate = course.startDate
        currentCourse.endDate = course.endDate

        // Remove any participants that are no longer part of the course
        val oldParticipants = currentCourse.participants.filter { currentParticipant -> !course.participants.any { currentParticipant.userId == it.userId } }
        participantRepository.delete(oldParticipants)

        // Add any new ones
        currentCourse.participants = course.participants.map {
            var participant = participantRepository.findOne(it.id)

            if (participant == null) {
                participant = Participant(userId = it.userId, course = currentCourse)
                participantRepository.save(participant)
            }

            participant
        }.toMutableSet()

        return currentCourse
    }

    fun deleteCourse(id: Long) {
        val course = courseRepository.findOne(id)
        auditClient.createEntry(
                action = "[CourseSystem] Delete Course",
                data = CourseAuditEntryDelete(id, course.toDTO(true))
        )
        courseRepository.delete(id)
    }
}