package dk.group11.rolesystem.course

import org.springframework.stereotype.Service
import java.util.*

@Service
class CourseService(
        private val courseRepo: CourseRepository
) {


    //val testParticipant: Participant = Participant()


    init {
        val testParticipants = mutableListOf(Participant())
        val course: List<Course> = Arrays.asList(
                Course("Test1", Date(), Date(), testParticipants),
                Course("Test2", Date(), Date()),
                Course("Test3", Date(), Date()),
                Course("Test4", Date(), Date()),
                Course("Test5", Date(), Date()))

        courseRepo.save(course)
    }

    fun getCourseById(courseId: Long): Course {
        return courseRepo.findOne(courseId)
    }

    fun getCourses(): Iterable<Course> {
        return courseRepo.findAll()
    }

    fun updateCourse(course: Course) {
        courseRepo.save(course)
    }

    fun deleteCourse(id:Long){
        courseRepo.delete(id)
    }

    fun saveParticipant(id: Long, participant: Participant) {
        val c = courseRepo.findOne(id)
        c.participant.add(participant)
        courseRepo.save(c)

    }

}