package dk.group11.coursesystem.services

import dk.group11.coursesystem.models.Course
import dk.group11.coursesystem.models.Lesson
import dk.group11.coursesystem.repositories.CourseRepository
import org.springframework.stereotype.Service

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