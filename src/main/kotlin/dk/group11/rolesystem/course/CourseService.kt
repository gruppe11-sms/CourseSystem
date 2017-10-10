package dk.group11.rolesystem.course

import dk.group11.rolesystem.lesson.Lesson
import dk.group11.rolesystem.room.Room
import org.springframework.stereotype.Service
import java.util.*

@Service
class CourseService {
    val s: List<Room> = Arrays.asList(
            Room("Test", 1, 2)
    )
    val testLesson: List<Lesson> = Arrays.asList(
            Lesson(s)
    )
    val testParticipants: List<Participant> = ArrayList<Participant>()

    val course: List<Course> = Arrays.asList(
            Course("Test1", Date(), Date(), testLesson, testParticipants),
            Course("Test2", Date(), Date(), testLesson, testParticipants),
            Course("Test3", Date(), Date(), testLesson, testParticipants),
            Course("Test4", Date(), Date(), testLesson, testParticipants),
            Course("Test5", Date(), Date(), testLesson, testParticipants)
    )

    fun getCourseById(id: Long): Course {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getCourses(): Iterable<Course> {
        return course
    }
}