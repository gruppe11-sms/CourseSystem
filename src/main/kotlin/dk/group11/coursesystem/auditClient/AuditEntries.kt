package dk.group11.coursesystem.auditClient

import dk.group11.coursesystem.controllers.AssignmentDTO
import dk.group11.coursesystem.controllers.toDTO
import dk.group11.coursesystem.models.Assignment
import java.util.*

data class AssignmentAuditEntry(val assignment: AssignmentDTO, val courseId: Long)

fun Assignment.toAuditEntry():
    AssignmentAuditEntry = AssignmentAuditEntry(assignment = this.toDTO(),courseId = this.course.id)