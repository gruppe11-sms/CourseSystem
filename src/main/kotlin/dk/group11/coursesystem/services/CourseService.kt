package dk.group11.coursesystem.services

import dk.group11.coursesystem.models.Assignment
import dk.group11.coursesystem.models.Course
import dk.group11.coursesystem.models.Lesson
import dk.group11.coursesystem.repositories.CourseRepository
import org.springframework.stereotype.Service
import java.util.function.Consumer

@Service
class CourseService(
        private val courseRepo: CourseRepository
) {

    fun getCourseById(courseId: Long): Course {
        if (courseRepo.exists(courseId)) {
            return courseRepo.findOne(courseId)
        }
        else return error("course doesn't exist")
    }

    fun getCourses(): Iterable<Course> {
        return courseRepo.findAll()
    }


    fun saveCourse(course: Course) {
        courseRepo.save(course)
    }

    fun deleteCourse(id:Long){
        courseRepo.delete(id)
    }

    fun getLessons(courseId: Long): MutableList<Lesson> {
        return courseRepo.findOne(courseId).lessons
    }


    fun addLesson(courseId: Long, lesson: Lesson) {
        val s: Course = courseRepo.findOne(courseId)
        s.lessons.add(lesson)
        courseRepo.save(s)
    }

}