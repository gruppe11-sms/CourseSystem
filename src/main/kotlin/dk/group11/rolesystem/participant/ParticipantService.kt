package dk.group11.rolesystem.participant

import dk.group11.rolesystem.course.Course
import dk.group11.rolesystem.course.CourseService
import org.springframework.stereotype.Service

@Service
class ParticipantService(val participantRepository: ParticipantRepository, val courseService: CourseService) {

    init {
        //var myParticipant : Participant = Participant()
    }

/*    fun getParticipants(courseId: Long) {
        return participantRepository.findByCourse_Id(courseId)
    }*/

    fun saveParticipant(courseId: Long, participant: Participant) {
        val myParticipant = Participant(getCourseById(courseId))
        participantRepository.save(myParticipant)
    }

    fun getCourseById(course_Id: Long): Course {
        return courseService.getCourseById(course_Id)
    }
}