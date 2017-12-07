package dk.group11.coursesystem.services

import dk.group11.coursesystem.clients.AuditClient
import dk.group11.coursesystem.clients.SimpleAssignmentAuditEntry
import dk.group11.coursesystem.controllers.AssignmentDTO
import dk.group11.coursesystem.controllers.SimpleAssignmentDTO
import dk.group11.coursesystem.controllers.UploadTask
import dk.group11.coursesystem.exceptions.BadRequestException
import dk.group11.coursesystem.models.*
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
                        private val activityService: ActivityService,
                        private val participantRepository: ParticipantRepository,
                        private val fileService: FileService,
                        private val securityService: SecurityService,
                        private val handInRepository: HandInRepository) {

    fun getAssignments(courseId: Long): Iterable<Assignment> {
        val course = courseRepository.findOne(courseId)
        auditClient.createEntry("[CourseSystem] See all assignments", course.id)
        val assignments = course.assignments.toList()

        val activities = activityService.getActivities(assignments.map { it.activityId })
        assignments.forEach { assignment ->
            assignment.activity = activities.find { it.id == assignment.activityId } ?: Activity()
        }

        return assignments

    }

    fun getAssignment(assignmentId: Long): Assignment {
        val assignment = assignmentRepository.findOne(assignmentId)
        auditClient.createEntry("[CourseSystem] See one assignment", assignmentId)

        assignment.activity = activityService.getActivity(assignment.activityId)

        assignment.handInAssignments = assignment.handInAssignments
                .filter { it.participant.userId == securityService.getId() }
                .toMutableList()

        return assignment
    }


    fun updateAssignment(assignmentDTO: AssignmentDTO): Assignment {
        val assignment = assignmentRepository.findOne(assignmentDTO.id) ?: throw BadRequestException("Assignment doesn't exist")
        assignment.description = assignmentDTO.description

        assignment.activity = Activity(
                id = assignment.activityId,
                title = assignmentDTO.title,
                startDate = assignmentDTO.startDate,
                endDate = assignmentDTO.endDate,
                participants = assignment.participants.map { ActivityParticipant(it.id) }.toMutableSet()
        )

        activityService.updateActivity(assignment)

        return assignment
    }

    fun updateAssignment(assignment: Assignment): Assignment {
        val currentAssignment = assignmentRepository.findOne(assignment.id) ?: throw BadRequestException("Assignment doesn't exists")

        currentAssignment.description = assignment.description

        currentAssignment.activity = activityService.updateActivity(assignment)

        return currentAssignment
    }

    fun deleteAssignment(assignmentId: Long) {
        val assignment = assignmentRepository.findOne(assignmentId)
        activityService.deleteActivity(assignment.activityId)
        assignmentRepository.delete(assignmentId)
    }

    fun createAssignment(courseId: Long, assignment: Assignment): Assignment {
        val course = courseRepository.findOne(courseId)
        assignment.course = course
        assignment.participants.addAll(course.participants)

        val activity = activityService.createActivity(assignment)
        assignment.activityId = activity.id

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

        val nameOfFile = task.file.originalFilename

        val assignment = assignmentRepository.findOne(task.assignmentId)
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
        val handInExists = participant.handInAssignments.find { it.assignment.id == task.assignmentId }

        //Checks if the assignment already exists and adds it to the handInAssignment. Else create a new handin.
        if (handInExists != null) {
            participant.handInAssignments.forEach {
                if (it.assignment.id == task.assignmentId) {
                    it.handInIds.add(uploadedFileResponse)
                    it.fileNames.add(nameOfFile)
                }
            }
        } else {
            val newHandIn =
                    HandInAssignment(handInIds = mutableListOf(uploadedFileResponse), assignment = assignment, participant = participant, fileNames = mutableListOf(nameOfFile))
            handInRepository.save(newHandIn)
            participant.handInAssignments.add(newHandIn)
            assignment.handInAssignments.add(newHandIn)
        }
        participantRepository.save(participant)
        assignmentRepository.save(assignment)
        return uploadedFileResponse
    }

    fun getAssignmentsByUserId(id: Long, amount: Int = -1): List<Assignment> {
        // Here be dragons, do not try to understand what this does, however if you do you will go down in history as a legend
        // (╯°□°)╯︵ ┻━┻
        // TODO test if this works on multiple assignments for the same user
        /*val assignments =
                participantRepository.findByUserId(id)
                        .flatMap { it.assignments }.toList()
                        .map {
                            it.activity = activityService.getActivity(it.activityId)
                            it
                        }
                        .map {
                            it.handInAssignments = it.handInAssignments
                                    .filter { it.participant.userId == id }
                                    .toMutableList()
                            it
                        }
                        .sortedBy { it.activity.endDate }
        */
        val participants = participantRepository.findByUserId(id)
        val assignments = participants.flatMap { it.assignments }.toList()
        val activities = activityService.getActivities(assignments.map { it.activityId })
        assignments.forEach { assignment ->
            assignment.activity = activities.first { it.id == assignment.activityId }
        }
        val assignmentsWithHandins = assignments.map {
            it.handInAssignments = it.handInAssignments
                    .filter { it.participant.userId == id }
                    .toMutableList()
            it
        }

        val orderedAssignments = assignmentsWithHandins.sortedBy { it.activity.endDate }

        return if (amount != -1) {
            orderedAssignments.take(amount)
        } else {
            orderedAssignments
        }

    }

}