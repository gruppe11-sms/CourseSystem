package dk.group11.rolesystem.Controllers

import dk.group11.rolesystem.Models.Evaluation
import dk.group11.rolesystem.Services.EvaluationService
import org.springframework.web.bind.annotation.*

@RestController
class EvaluationController(val evaluationService: EvaluationService) {
    @GetMapping("/api/courses/evaluations/{evaluationId}")
    fun getEvaluation(@PathVariable evaluationId: Long): Evaluation {
        return evaluationService.getEvaluation(evaluationId)
    }

    @GetMapping("/api/courses/evaluations")
    fun getAllEvaluations(): MutableIterable<Evaluation>? {
        return evaluationService.getAll()
    }

    @PutMapping("/api/courses/{courseId}/evaluations/{evaluationId}")
    fun updateEvaluation(@PathVariable evaluationId: Long, @RequestBody evaluation: Evaluation) {
        evaluationService.updateEvaluation(evaluationId, evaluation)
    }

    @DeleteMapping("/api/courses/evaluations/{evaluationId}")
    fun deleteEvaluation(@PathVariable evaluationId: Long) {
        evaluationService.deleteEvaluation(evaluationId)
    }
}