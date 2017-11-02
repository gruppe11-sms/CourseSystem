package dk.group11.rolesystem.repositories

import dk.group11.rolesystem.models.Evaluation
import org.springframework.data.repository.CrudRepository


interface EvaluationRepository : CrudRepository<Evaluation,Long> {

    //fun findByCourseId(id: Long): Iterable<Evaluation>

    //fun findByCourseIdAndEvaluationId(courseId : Long, lessonId : Long) : Evaluation

}