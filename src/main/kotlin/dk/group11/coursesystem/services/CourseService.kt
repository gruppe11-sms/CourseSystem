package dk.group11.coursesystem.services

import dk.group11.coursesystem.clients.AuditClient
import dk.group11.coursesystem.clients.CalendarClient
import dk.group11.coursesystem.clients.CourseAuditEntryDelete
import dk.group11.coursesystem.clients.CourseAuditEntryGet
import dk.group11.coursesystem.exceptions.BadRequestException
import dk.group11.coursesystem.models.Course
import dk.group11.coursesystem.repositories.CourseRepository
import org.springframework.stereotype.Service

@Service
class CourseService(private val courseRepository: CourseRepository,
                    private val auditClient: AuditClient,
                    private val calendarClient: CalendarClient,
                    private val dtoService: DtoService) {

    fun getCourseById(courseId: Long): Course {
        auditClient.createEntry("[CourseSystem] Get Course", CourseAuditEntryGet(courseId))
        if (courseRepository.exists(courseId)) {
            return courseRepository.findOne(courseId)
        } else throw BadRequestException("Course doesn't exist for course id '$courseId'")
    }

    fun getCourses(): Iterable<Course> {
        auditClient.createEntry("[CourseSystem] Get All Courses", "")
        /* The optimal solution for this implementation would be to ask the rolesystem
         * for what type of user is currently logged in, and we would have returned the
         * specific courses depending on the logged in type of user. (admin/normal user etc. */
        // return participantRepository.findByUserId(securityService.getId()).map { it.course }
        return courseRepository.findAll()
    }

    fun createCourse(course: Course): Course {
        val newCourse = courseRepository.save(course)
        auditClient.createEntry(
                action = "[CourseSystem] Create Course",
                data = dtoService.convert(newCourse, recursive = false)
        )
        return course
    }

    fun updateCourse(course: Course): Course {
        auditClient.createEntry(action = "[CourseSystem] Save Course", data = course)
        return courseRepository.save(course)
    }

    fun deleteCourse(id: Long) {
        val course = courseRepository.findOne(id)
        auditClient.createEntry(
                action = "[CourseSystem] Delete Course",
                data = CourseAuditEntryDelete(id, dtoService.convert(course))
        )
        courseRepository.delete(id)
    }
}