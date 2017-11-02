package dk.group11.coursesystem.controllers

import dk.group11.coursesystem.models.Evaluation
import dk.group11.coursesystem.services.EvaluationService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/courses")
class EvaluationController(val evaluationService: EvaluationService) {
    @GetMapping("/evaluations/{evaluationId}")
    fun getEvaluation(@PathVariable evaluationId: Long): EvaluationDTO {
        return evaluationService.getEvaluation(evaluationId).toDTO()
    }

    @GetMapping("/evaluations")
    fun getAllEvaluations(): Iterable<EvaluationDTO> {
        return evaluationService.getAll().map { it.toDTO() }
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