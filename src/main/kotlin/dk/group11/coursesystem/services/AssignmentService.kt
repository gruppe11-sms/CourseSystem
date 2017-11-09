package dk.group11.coursesystem.services

import dk.group11.coursesystem.auditClient.AssignmentAuditEntry
import dk.group11.coursesystem.auditClient.AuditClient
import dk.group11.coursesystem.auditClient.toAuditEntry
import dk.group11.coursesystem.models.Assignment
import dk.group11.coursesystem.repositories.AssignmentRepository
import dk.group11.coursesystem.repositories.CourseRepository
import dk.group11.coursesystem.security.ISecurityService
import org.springframework.stereotype.Service

@Service
class AssignmentService(
        private val assignmentRepository: AssignmentRepository,
        private val courseRepository: CourseRepository,
        private val auditClient: AuditClient,
        private val security: ISecurityService
) {

    fun getAssignments(courseId: Long): Iterable<Assignment> {
        return assignmentRepository.findByCourseId(courseId)
    }

    fun getOneAssignment(assignmentId: Long): Assignment {
        return assignmentRepository.findOne(assignmentId)
    }


    fun updateAssignment(assignmentId: Long, assignment: Assignment): String {
        if (assignmentRepository.exists(assignmentId) && assignment.id == assignmentId)
            assignmentRepository.save(assignment)
        else
            return "Sorry entry does not exist!"
        return "assignments successfully updated!"
    }

    fun deleteAssignment(assignmentId: Long) {
        assignmentRepository.delete(assignmentId)
    }

    fun getAllAssignments(): MutableIterable<Assignment> {
        return assignmentRepository.findAll()
    }

    fun createAssignment(courseId: Long, assignment: Assignment) {
        val course = courseRepository.findOne(courseId)
        course.participants.forEach { it.assignments.add(assignment) }
        course.assignments.add(assignment)
        assignment.course = course
        courseRepository.save(course)

        auditClient.createEntry("[CourseSystem] Assignment created", assignment.toAuditEntry(), security.getToken())

    }

}

