package dk.group11.coursesystem.services

import dk.group11.coursesystem.models.Lesson
import dk.group11.coursesystem.repositories.CourseRepository
import dk.group11.coursesystem.repositories.LessonRepository
import org.springframework.stereotype.Service

@Service
class LessonService(val lessonRepository: LessonRepository, val courseRepository: CourseRepository) {

    fun createLesson(lesson: Lesson, courseId: Long): Lesson{
        val course = courseRepository.findOne(courseId)
        lesson.course = course
        return lessonRepository.save(lesson)

    }

    fun getLesson(lessonId: Long): Lesson{
        return lessonRepository.findOne(lessonId)
    }

    fun updateLesson(lesson: Lesson): Lesson {
        return lessonRepository.save(lesson)

    }

    fun deleteLesson(lessonId: Long){
        lessonRepository.delete(lessonId)
    }

    fun getLessons(courseId: Long): List<Lesson> {
        return courseRepository.findOne(courseId).lessons
    }



}