package dk.group11.rolesystem.Services

import dk.group11.rolesystem.Models.Assignment
import dk.group11.rolesystem.Repositories.AssignmentRepository
import org.springframework.stereotype.Service

@Service
class AssignmentService(private val assignmentRepository: AssignmentRepository) {

    fun getAssignments(courseId: Long): Iterable<Assignment> {
        return assignmentRepository.findByCourse_Id(courseId)
    }

    fun getOneAssignment(assignmentId: Long): Assignment {
        return assignmentRepository.findOne(assignmentId)
    }

    fun addAssignment(courseId: Long, assignment: Assignment) {
        assignment.course.id = courseId
        assignmentRepository.save(assignment)
    }

    fun updateAssignment(assignmentId: Long, assignment: Assignment): String {
        if (assignmentRepository.exists(assignmentId))
            assignmentRepository.save(assignment)
        else
            return "Sorry entry does not exist!"
        return "assignment successfully updated!"
    }

    fun deleteAssignment(assignmentId: Long) {
        assignmentRepository.delete(assignmentId)
    }

    fun getAllAssignments(): MutableIterable<Assignment>? {
        return assignmentRepository.findAll()
    }

}

