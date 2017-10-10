package dk.group11.rolesystem.evaluation

import org.springframework.data.repository.CrudRepository


interface EvaluationRepository : CrudRepository<Evaluation,Long> {

    fun findByCourseId(id: Long): Iterable<Evaluation>

    fun findByCourseIdAndEvaluationId(courseid : Long, lessonid : Long) : Evaluation
}