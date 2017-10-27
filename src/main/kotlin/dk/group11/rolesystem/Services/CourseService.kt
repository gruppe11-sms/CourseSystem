package dk.group11.rolesystem.Services

import dk.group11.rolesystem.Models.Course
import dk.group11.rolesystem.Models.Lesson
import dk.group11.rolesystem.Repositories.CourseRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CourseService(
        private val courseRepo: CourseRepository
) {

    fun getCourseById(courseId: Long): Course {
        return courseRepo.findOne(courseId)
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