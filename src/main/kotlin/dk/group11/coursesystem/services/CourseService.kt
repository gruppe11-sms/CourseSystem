package dk.group11.coursesystem.services

import dk.group11.coursesystem.auditClient.AuditClient
import dk.group11.coursesystem.controllers.CourseDTO
import dk.group11.coursesystem.controllers.toDTO
import dk.group11.coursesystem.exceptions.BadRequestException
import dk.group11.coursesystem.models.Course
import dk.group11.coursesystem.repositories.CourseRepository
import dk.group11.coursesystem.security.SecurityService
import org.springframework.stereotype.Service

@Service
class CourseService(
        private val courseRepo: CourseRepository, private val auditClient: AuditClient, private val securityService: SecurityService
) {
    class getCourseAuditEntry(val courseId: Long)
    class deleteCourseAuditEntry(val courseId: Long, courseDTO: CourseDTO)

    fun getCourseById(courseId: Long): Course {
        auditClient.createEntry("[CourseSystem] Get Course", getCourseAuditEntry(courseId))
        if (courseRepo.exists(courseId)) {
            return courseRepo.findOne(courseId)
        } else throw BadRequestException("Course Doesnt exist for course id '$courseId'")
    }

    fun getCourses(): Iterable<Course> {
        auditClient.createEntry("[CourseSystem] Get All Courses", "")
        return courseRepo.findAll()
    }

    fun createCourse(course: Course): Course {
        courseRepo.save(course)
        auditClient.createEntry("[CourseSystem] Create Course", course.toDTO(false))
        return course
    }

    fun updateCourse(course: Course): Course {
        auditClient.createEntry("[CourseSystem] Save Course", course)
        return courseRepo.save(course)
    }

    fun deleteCourse(id:Long){
        val course = courseRepo.findOne(id)
        auditClient.createEntry("[CourseSystem] Delete Course", deleteCourseAuditEntry(id, courseDTO = course.toDTO(true)))
        courseRepo.delete(id)
    }


}