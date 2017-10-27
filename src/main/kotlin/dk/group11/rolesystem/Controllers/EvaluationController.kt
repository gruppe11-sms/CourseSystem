package dk.group11.rolesystem.Controllers

import dk.group11.rolesystem.Models.Evaluation
import dk.group11.rolesystem.Services.EvaluationService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/courses")
class EvaluationController(val evaluationService: EvaluationService) {
    @GetMapping("/evaluations/{evaluationId}")
    fun getEvaluation(@PathVariable evaluationId: Long): Evaluation {
        return evaluationService.getEvaluation(evaluationId)
    }

    @GetMapping("/evaluations")
    fun getAllEvaluations(): MutableIterable<Evaluation>? {
        return evaluationService.getAll()
    }

    @PutMapping("/{courseId}/evaluations/{evaluationId}")
    fun updateEvaluation(@PathVariable evaluationId: Long, @RequestBody evaluation: Evaluation) {
        evaluationService.updateEvaluation(evaluationId, evaluation)
    }

    @DeleteMapping("/evaluations/{evaluationId}")
    fun deleteEvaluation(@PathVariable evaluationId: Long) {
        evaluationService.deleteEvaluation(evaluationId)
    }
}