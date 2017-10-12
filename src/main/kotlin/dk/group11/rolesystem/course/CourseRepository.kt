package dk.group11.rolesystem.course

import org.springframework.data.repository.CrudRepository

interface CourseRepository : CrudRepository<Course, Long> {
    //fun findCourseByCourseIdAndSaveParticipant(courseId: Long, participant: Participant)
}