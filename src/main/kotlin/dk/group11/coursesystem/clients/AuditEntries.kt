package dk.group11.coursesystem.clients

import dk.group11.coursesystem.controllers.AssignmentDTO
import dk.group11.coursesystem.controllers.CourseDTO
import dk.group11.coursesystem.controllers.LessonDTO
import dk.group11.coursesystem.controllers.SimpleAssignmentDTO

data class LessonAuditEntryCreation(val lesson: LessonDTO, val courseId: Long)
data class LessonAuditEntryUpdate(val before: LessonDTO, val after: LessonDTO)
data class LessonAuditEntryDelete(val deleted: LessonDTO, val lessonId: Long)

data class AssignmentAuditEntry(val assignment: AssignmentDTO, val courseId: Long)
data class SimpleAssignmentAuditEntry(val assignment: SimpleAssignmentDTO, val courseId: Long)

data class CourseAuditEntryGet(val courseId: Long)
data class CourseAuditEntryDelete(val courseId: Long, val courseDTO: CourseDTO)