package dk.group11.rolesystem.services

import dk.group11.rolesystem.models.Evaluation
import dk.group11.rolesystem.repositories.EvaluationRepository
import org.springframework.stereotype.Service

@Service
class EvaluationService(private val evaluationRepository: EvaluationRepository) {

    fun getEvaluation(evaluationId: Long): Evaluation {
        return evaluationRepository.findOne(evaluationId)
    }

    fun deleteEvaluation(evaluationId: Long) {
        evaluationRepository.delete(evaluationId)
    }

    fun deleteEvaluationsById(evaluationIdList: List<Long>) {
        evaluationIdList.forEach { evaluationRepository.delete(it) }
    }

    fun updateEvaluation(evaluationId: Long, evaluation: Evaluation) {
        if (evaluationRepository.exists(evaluationId))
            evaluationRepository.save(evaluation)
    }

    fun getAll(): Iterable<Evaluation> {
        return evaluationRepository.findAll()
    }


}