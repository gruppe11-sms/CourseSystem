package dk.group11.rolesystem.course

import org.springframework.stereotype.Service
import java.util.*

@Service
class CourseService(
        private val courseRepo: CourseRepository
) {
    init {
        val course: List<Course> = Arrays.asList(
                Course("Test1", Date(), Date()),
                Course("Test2", Date(), Date()),
                Course("Test3", Date(), Date()),
                Course("Test4", Date(), Date()),
                Course("Test5", Date(), Date()))

        //course[0].participant.add(Participant(course[0])
        courseRepo.save(course)
        //val myCourse : Course = Course("Test work please", Date(), Date())
        //val myParticipant : Participant = Participant(myCourse)
        //myCourse.participant.add(myParticipant)
        //courseRepo.save(myCourse)
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



}