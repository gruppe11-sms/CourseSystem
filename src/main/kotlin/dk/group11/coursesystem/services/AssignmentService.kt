package dk.group11.coursesystem.services

import dk.group11.coursesystem.clients.AuditClient
import dk.group11.coursesystem.clients.CalendarClient
import dk.group11.coursesystem.clients.FileClient
import dk.group11.coursesystem.clients.SimpleAssignmentAuditEntry
import dk.group11.coursesystem.controllers.ServerTask
import dk.group11.coursesystem.controllers.SimpleAssignmentDTO
import dk.group11.coursesystem.exceptions.BadRequestException
import dk.group11.coursesystem.models.AssembledAssignment
import dk.group11.coursesystem.models.Assignment
import dk.group11.coursesystem.models.HandInAssignment
import dk.group11.coursesystem.repositories.AssignmentRepository
import dk.group11.coursesystem.repositories.CourseRepository
import dk.group11.coursesystem.repositories.ParticipantRepository
import dk.group11.coursesystem.security.SecurityService
import org.springframework.stereotype.Service

// TODO add audit entries
@Service
class AssignmentService(private val assignmentRepository: AssignmentRepository,
                        private val courseRepository: CourseRepository,
                        private val auditClient: AuditClient,
                        private val participantRepository: ParticipantRepository,
                        private val calendarClient: CalendarClient,
                        private val fileService: FileClient,
                        private val securityService: SecurityService) {

    fun getAssignments(courseId: Long): Iterable<AssembledAssignment> {
        val course = courseRepository.findOne(courseId)
        auditClient.createEntry("[CourseSystem] See all assignments", course.id)
        return course.assignments.map { assemble(it) }
    }

    fun getAssignment(assignmentId: Long): AssembledAssignment {
        val assignment = assignmentRepository.findOne(assignmentId)
        auditClient.createEntry("[CourseSystem] See one assignment", assignmentId)
        return assemble(assignment)
    }

    fun updateAssignment(assignmentId: Long, assignment: AssembledAssignment): AssembledAssignment {
        if (assignmentRepository.exists(assignmentId) && assignment.id == assignmentId)
            assignmentRepository.save(assignment.getAssignment())
        else
            throw BadRequestException("Assignment wasn't updated")
        calendarClient.updateActivity(assignment.getActivity())
        return assignment
    }

    fun deleteAssignment(assignmentId: Long) {
        val assignment = assignmentRepository.findOne(assignmentId)
        calendarClient.deleteActivity(assignment.activityId)
        assignmentRepository.delete(assignmentId)
    }

    fun getAllAssignments(): Iterable<AssembledAssignment> {
        return assignmentRepository.findAll().map { assemble(it) }
    }

    fun createAssignment(courseId: Long, assignment: AssembledAssignment): AssembledAssignment {
        // TODO clean up crazy code
        val course = courseRepository.findOne(courseId)
        assignment.course = course

        val activity = calendarClient.createActivity(assignment.getActivity())
        assignment.activityId = activity.id

        assignmentRepository.save(assignment.getAssignment())
        assignment.participants.addAll(course.participants)

        course.participants.forEach { it.assignments.add(assignment.getAssignment()) }
        course.assignments.add(assignment.getAssignment())
        courseRepository.save(course)

        auditClient.createEntry(
                action = "[CourseSystem] Assignment created",
                data = SimpleAssignmentAuditEntry(
                        assignment = SimpleAssignmentDTO(id = assignment.id, description = assignment.description),
                        courseId = assignment.course.id)
        )
        return assignment
    }

    fun uploadAssignment(task: ServerTask) {
        //Audit
        auditClient.createEntry("[CourseSystem] Assignment uploaded", task)

        // Finds a participant
        val participant = assignmentRepository
                .findOne(task.assignmentId)
                .participants
                .find { it.userId == securityService.getId() }

        //Uploads file
        val uploadedFileResponse = fileService.storeFile(task.file)

        //Finds a handIn or Null
        val handIn = participant
                ?.handInAssignments
                ?.find { it.assignmentId == task.assignmentId }


        //Checks if the assignment already exists and adds it to the handInAssignment
        if (handIn != null) {
            handIn.handInIds.add(uploadedFileResponse)
        } else {
            val newHandin = HandInAssignment(handInIds = mutableListOf(uploadedFileResponse), assignmentId = task.assignmentId)
            participant?.handInAssignments?.add(newHandin)
        }
    }


    fun getAssignmentsByUserId(id: Long): List<Assignment> {
        return participantRepository.findByUserId(id).flatMap { it.assignments }
    }

    fun assemble(assignment: Assignment): AssembledAssignment =
            AssembledAssignment(assignment, calendarClient.getActivity(assignment.activityId))
}

