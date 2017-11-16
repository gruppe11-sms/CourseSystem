package dk.group11.coursesystem.auditClient

import dk.group11.coursesystem.models.Assignment
import java.util.*

data class AssignmentAuditEntry(val title: String, val description: String, val courseID: Long, val startDate: Date, val endDate: Date, val assignmentID: Long)

fun Assignment.toAuditEntry():
        AssignmentAuditEntry = AssignmentAuditEntry(title = this.title, description = this.description, courseID = this.course.id, startDate = this.startdate, endDate = this.enddate, assignmentID = this.id)