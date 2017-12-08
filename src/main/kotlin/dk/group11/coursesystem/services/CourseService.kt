package dk.group11.coursesystem.services

import dk.group11.coursesystem.COURSE_MANAGEMENT_ROLE
import dk.group11.coursesystem.clients.*
import dk.group11.coursesystem.controllers.CourseDTO
import dk.group11.coursesystem.exceptions.BadRequestException
import dk.group11.coursesystem.helpers.concat
import dk.group11.coursesystem.models.Activity
import dk.group11.coursesystem.models.Course
import dk.group11.coursesystem.models.Participant
import dk.group11.coursesystem.repositories.CourseRepository
import dk.group11.coursesystem.repositories.ParticipantRepository
import dk.group11.coursesystem.security.SecurityService
import org.springframework.stereotype.Service
import javax.transaction.Transactional

data class updateCourseAuditEntry(val before: CourseDTO, val after: CourseDTO)

@Service
class CourseService(private val courseRepository: CourseRepository,
                    private val auditClient: AuditClient,
                    private val calendarClient: CalendarClient,
                    private val participantRepository: ParticipantRepository,
                    private val securityService: SecurityService,
                    private val roleClient: RoleClient) {

    fun getCourseById(courseId: Long): Course {
        auditClient.createEntry("[CourseSystem] Get Course", CourseAuditEntryGet(courseId))
        val course = courseRepository.findOne(courseId)
        val activities = calendarClient.getActivity(*course.assignments.map { it.activityId }.concat(course.lessons.map { it.activityId }).toLongArray())
        if (course != null) {
            course.assignments.forEach { assignment ->
                assignment.activity = activities.find { it.id == assignment.activityId } ?: Activity()
            }
            course.lessons.forEach { lesson ->
                lesson.activity = activities.find { it.id == lesson.activityId } ?: Activity()
            }
            return course
        } else {
            throw BadRequestException("Course doesn't exist for course id '$courseId'")
        }
    }

    fun getCourses(): Iterable<Course> {
        auditClient.createEntry("[CourseSystem] Get All Courses", "")

        return if (roleClient.hasRoles(securityService.getToken(), COURSE_MANAGEMENT_ROLE)) {
            courseRepository.findAll()
        } else {
            val userId = securityService.getId()
            val courses = participantRepository.findByUserId(userId)
                    .map { it.course }


            courses.forEach {
                it.assignments = it.assignments.map {
                    it.handInAssignments = it.handInAssignments
                            .filter { it.participant.userId == userId }
                            .toMutableList()
                    it
                }.toMutableSet()
            }

            courses
        }
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

        println("Number of participants" + course.participants.size)

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
            println("looking for participant $it")
            var participant = participantRepository.findOne(it.id)

            if (participant == null) {
                println("Participant ${it} does not exist, creating")
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