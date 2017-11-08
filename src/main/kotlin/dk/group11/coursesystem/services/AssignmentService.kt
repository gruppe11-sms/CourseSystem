package dk.group11.coursesystem.services

import dk.group11.coursesystem.models.Assignment
import dk.group11.coursesystem.repositories.AssignmentRepository
import org.springframework.stereotype.Service

@Service
class AssignmentService(private val assignmentRepository: AssignmentRepository) {

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

}

