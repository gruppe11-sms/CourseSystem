package dk.group11.coursesystem.services

import dk.group11.coursesystem.clients.AuditClient
import dk.group11.coursesystem.clients.FileClient
import dk.group11.coursesystem.clients.toAuditEntry
import dk.group11.coursesystem.exceptions.BadRequestException
import dk.group11.coursesystem.models.Assignment
import dk.group11.coursesystem.models.HandInAssignment
import dk.group11.coursesystem.repositories.AssignmentRepository
import dk.group11.coursesystem.repositories.CourseRepository
import dk.group11.coursesystem.repositories.ParticipantRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class AssignmentService(
        private val assignmentRepository: AssignmentRepository,
        private val courseRepository: CourseRepository,
        private val auditClient: AuditClient,
        //private val security: ISecurityService,
        private val participantRepository: ParticipantRepository,
        private val fileService: FileClient
) {

    fun getAssignments(courseId: Long): Iterable<Assignment> {
        val course = courseRepository.findOne(courseId)
        auditClient.createEntry("[CourseSystem] See all assignments", course.assignments.map { it.toAuditEntry() })
        return course.assignments
    }

    fun getOneAssignment(assignmentId: Long): Assignment {
        auditClient.createEntry("[CourseSystem] See one assignment", assignmentId)
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

    fun createAssignment(courseId: Long, assignment: Assignment): Assignment {
        val course = courseRepository.findOne(courseId)
        assignment.course = course

//        courseRepository.save(course)
        assignmentRepository.save(assignment)
        assignment.participants.addAll(course.participants)

        course.participants.forEach { it.assignments.add(assignment) }
        course.assignments.add(assignment)
        courseRepository.save(course)

        auditClient.createEntry("[CourseSystem] Assignment created", assignment.toAuditEntry())
        return assignment
    }

    fun uploadAssignment(assignmentId: Long, participantId: Long, file: MultipartFile) {
        auditClient.createEntry("[CourseSystem] Assignment uploaded", assignmentId)
        //TODO valider bruger rettigheder smid ex hvis denne er falsk

        //uploads file
        var uploadedFileResponse = fileService.storeFile(file)

        //checks if the assignment already exists and adds it to the handInAssignment
        var participant = participantRepository.findOne(participantId)

        var handIn = participant.handInAssignments.find{ it.assignmentId==assignmentId }
        if (handIn != null){
            handIn.handInIds.add(uploadedFileResponse)
        }else {
            var newHandin = HandInAssignment(handInIds = mutableListOf(uploadedFileResponse),assignmentId = assignmentId)
            participant.handInAssignments.add(newHandin)
        }

    }

    fun getAssigmentsByUserId(id: Long): List<Assignment> {
        return participantRepository.findByUserId(id).flatMap { it.assignments }
    }

}

