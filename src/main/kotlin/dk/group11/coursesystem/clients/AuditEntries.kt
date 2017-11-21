package dk.group11.coursesystem.clients

import dk.group11.coursesystem.controllers.AssignmentDTO
import dk.group11.coursesystem.controllers.toDTO
import dk.group11.coursesystem.models.Assignment

data class AssignmentAuditEntry(val assignment: AssignmentDTO, val courseId: Long)

fun Assignment.toAuditEntry():
        AssignmentAuditEntry = AssignmentAuditEntry(assignment = this.toDTO(true), courseId = this.course.id)