package dk.group11.rolesystem.Services

import dk.group11.rolesystem.Models.Lesson
import dk.group11.rolesystem.Repositories.LessonRepository
import org.springframework.stereotype.Service

@Service
class LessonService(var lessonRepository: LessonRepository) {

    fun findAllLessons(): Iterable<Lesson> {
        return lessonRepository.findAll()
    }

    fun findOneLesson(lessonId: Long): Lesson {
        return lessonRepository.findOne(lessonId)
    }

    fun updateLesson(lesson: Lesson) {
        lessonRepository.save(lesson)
    }

    fun deleteLesson(lessonId: Long) {
        lessonRepository.delete(lessonId)
    }

}