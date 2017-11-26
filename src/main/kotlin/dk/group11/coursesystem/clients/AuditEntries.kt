package dk.group11.coursesystem.clients

import dk.group11.coursesystem.controllers.AssignmentDTO
import dk.group11.coursesystem.controllers.CourseDTO
import dk.group11.coursesystem.controllers.SimpleAssignmentDTO
import dk.group11.coursesystem.controllers.SimpleLessonDTO

data class LessonAuditEntryCreation(val lesson: SimpleLessonDTO, val courseId: Long)
data class LessonAuditEntryUpdate(val before: SimpleLessonDTO, val after: SimpleLessonDTO)
data class LessonAuditEntryDelete(val deleted: SimpleLessonDTO, val lessonId: Long)

data class AssignmentAuditEntry(val assignment: AssignmentDTO, val courseId: Long)
data class SimpleAssignmentAuditEntry(val assignment: SimpleAssignmentDTO, val courseId: Long)

data class CourseAuditEntryGet(val courseId: Long)
data class CourseAuditEntryDelete(val courseId: Long, val courseDTO: CourseDTO)