package dk.group11.coursesystem.services

import dk.group11.coursesystem.clients.AuditClient
import dk.group11.coursesystem.clients.CalendarClient
import dk.group11.coursesystem.clients.SimpleAssignmentAuditEntry
import dk.group11.coursesystem.controllers.AssignmentDTO
import dk.group11.coursesystem.controllers.SimpleAssignmentDTO
import dk.group11.coursesystem.controllers.UploadTask
import dk.group11.coursesystem.exceptions.BadRequestException
import dk.group11.coursesystem.models.Activity
import dk.group11.coursesystem.models.Assignment
import dk.group11.coursesystem.models.HandInAssignment
import dk.group11.coursesystem.models.UploadedFile
import dk.group11.coursesystem.models.Participant
import dk.group11.coursesystem.repositories.AssignmentRepository
import dk.group11.coursesystem.repositories.CourseRepository
import dk.group11.coursesystem.repositories.HandInRepository
import dk.group11.coursesystem.repositories.ParticipantRepository
import dk.group11.coursesystem.security.SecurityService
import dk.group11.coursesystem.services.fileService.storage.FileService
import org.springframework.stereotype.Service

// TODO add audit entries
@Service
class AssignmentService(private val assignmentRepository: AssignmentRepository,
                        private val courseRepository: CourseRepository,
                        private val auditClient: AuditClient,
                        private val calendarClient: CalendarClient,
                        private val participantRepository: ParticipantRepository,
                        private val fileService: FileService,
                        private val securityService: SecurityService,
                        private val handInRepository: HandInRepository) {

    fun getAssignments(courseId: Long): Iterable<Assignment> {
        val course = courseRepository.findOne(courseId)
        auditClient.createEntry("[CourseSystem] See all assignments", course.id)
        return course.assignments.map {
            it.copy(activity = calendarClient.getActivity(it.activityId))
        }

    }

    fun getAssignment(assignmentId: Long): Assignment {
        val assignment = assignmentRepository.findOne(assignmentId)
        auditClient.createEntry("[CourseSystem] See one assignment", assignmentId)

        assignment.activity = calendarClient.getActivity(assignment.activityId)

        return assignment
    }


    fun updateAssignment(assignmentDTO: AssignmentDTO): Assignment {
        val assignment = assignmentRepository.findOne(assignmentDTO.id) ?: throw BadRequestException("Assignment doesn't exist")
        assignment.description = assignmentDTO.description

        val activity = Activity(
                id = assignment.activityId,
                title = assignmentDTO.title,
                startDate = assignmentDTO.startDate,
                endDate = assignmentDTO.endDate
        )
        calendarClient.updateActivity(activity)

        return assignment
    }

    fun updateAssignment(assignment: Assignment): Assignment {
        val currentAssignment = assignmentRepository.findOne(assignment.id) ?: throw BadRequestException("Assignment doesn't exists")

        currentAssignment.description = assignment.description

        calendarClient.updateActivity(assignment.activity)
        currentAssignment.activity = assignment.activity

        return currentAssignment
    }

    fun deleteAssignment(assignmentId: Long) {
        val assignment = assignmentRepository.findOne(assignmentId)
        calendarClient.deleteActivity(assignment.activityId)
        assignmentRepository.delete(assignmentId)
    }

    fun getAllAssignments(): Iterable<Assignment> {
        return assignmentRepository.findAll().map {
            it.copy(activity = calendarClient.getActivity(it.activityId))
        }
    }

    fun createAssignment(courseId: Long, assignment: Assignment): Assignment {
        val activity = calendarClient.createActivity(assignment.activity)
        assignment.activityId = activity.id
        val course = courseRepository.findOne(courseId)
        assignment.course = course
        assignment.participants.addAll(course.participants)

        assignmentRepository.save(assignment)
        auditClient.createEntry(
                action = "[CourseSystem] Assignment created",
                data = SimpleAssignmentAuditEntry(
                        assignment = SimpleAssignmentDTO(id = assignment.id, description = assignment.description),
                        courseId = assignment.course.id)
        )
        return assignment
    }

    fun createAssignment(courseId: Long, assignmentDTO: AssignmentDTO): Assignment {
        val assignment = Assignment(
                activity = Activity(
                        title = assignmentDTO.title,
                        startDate = assignmentDTO.startDate,
                        endDate = assignmentDTO.endDate
                ),
                description = assignmentDTO.description,
                participants = assignmentDTO.participants
                        .map { Participant(it.userId) }.toMutableSet()
        )

        return createAssignment(courseId, assignment)
    }
    fun uploadAssignment(task: UploadTask): UploadedFile {
        auditClient.createEntry("[CourseSystem] Assignment uploaded", task.assignmentId)


        val assignment = assignmentRepository
                .findOne(task.assignmentId)
                ?: throw BadRequestException("Assignment not found")

        // Finds a participant
        // TODO refactor such that find is done on the database level and not through a kotlin stream
        val participant = assignment.participants
                .find { it.userId == securityService.getId() }
                ?: throw BadRequestException("For some reason you do not exists on this assignment")

        //Uploads file and returns the id for the stored file
        val uploadedFileResponse = UploadedFile(fileService.storeFile(task.file))

        //Finds a handIn or Null
        // TODO check in the database table handins if the assignment exists based on some criterias

        val handInExists = participant.handInAssignments.find { it.assignmentId == task.assignmentId }

        //Checks if the assignment already exists and adds it to the handInAssignment. Else create a new handin.
        if (handInExists != null) {
            participant.handInAssignments.forEach {
                if (it.assignmentId == task.assignmentId) {
                    it.handInIds.add(uploadedFileResponse)
                }
            }
        } else {
            val newHandIn = HandInAssignment(handInIds = mutableListOf(uploadedFileResponse), assignmentId = task.assignmentId, participant = participant)
            handInRepository.save(newHandIn)
            participant.handInAssignments.add(newHandIn)
        }
        participantRepository.save(participant)

        return uploadedFileResponse
    }

    fun getAssignmentsByUserId(id: Long): List<Assignment> {
        return participantRepository.findByUserId(id).flatMap { it.assignments }
    }

}