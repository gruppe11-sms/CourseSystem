package dk.group11.coursesystem.repositories

import dk.group11.coursesystem.models.Evaluation
import org.springframework.data.repository.CrudRepository


interface EvaluationRepository : CrudRepository<Evaluation,Long> {

    fun existsByGrade(grade: String): Boolean

    //fun findByCourseId(id: Long): Iterable<Evaluation>

    //fun findByCourseIdAndEvaluationId(courseId : Long, lessonId : Long) : Evaluation

}