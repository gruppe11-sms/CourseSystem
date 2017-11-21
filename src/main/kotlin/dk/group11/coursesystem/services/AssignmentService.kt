package dk.group11.coursesystem.services

import dk.group11.coursesystem.clients.AuditClient
import dk.group11.coursesystem.clients.toAuditEntry
import dk.group11.coursesystem.exceptions.BadRequestException
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
        val course = courseRepository.findOne(courseId)
        auditClient.createEntry("[CourseSystem] See all assignments",course.assignments.map { it.toAuditEntry() } , security.getToken())
        return course.assignments
    }

    fun getOneAssignment(assignmentId: Long): Assignment {
        auditClient.createEntry("[CourseSystem] See one assignment", assignmentId, security.getToken())
        return assignmentRepository.findOne(assignmentId)
    }


    fun updateAssignment(assignmentId: Long, assignment: Assignment): Assignment {
        if (assignmentRepository.exists(assignmentId) && assignment.id == assignmentId)
            assignmentRepository.save(assignment)
        else
            throw BadRequestException("Assignment wasn't updated")
        return assignment
    }

    fun deleteAssignment(assignmentId: Long) {
        assignmentRepository.delete(assignmentId)
    }

    fun getAllAssignments(): MutableIterable<Assignment> {
        return assignmentRepository.findAll()
    }

    fun createAssignment(courseId: Long, assignment: Assignment) : Assignment {
        val course = courseRepository.findOne(courseId)
        assignment.course = course

//        courseRepository.save(course)
        assignmentRepository.save(assignment)
        assignment.participants.addAll(course.participants)

        course.participants.forEach { it.assignments.add(assignment) }
        course.assignments.add(assignment)
        courseRepository.save(course)

        auditClient.createEntry("[CourseSystem] Assignment created", assignment.toAuditEntry(), security.getToken())
        return assignment
    }

}

