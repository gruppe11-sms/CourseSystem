package dk.group11.coursesystem.controllers

import dk.group11.coursesystem.models.Evaluation
import dk.group11.coursesystem.services.DtoService
import dk.group11.coursesystem.services.EvaluationService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/courses")
class EvaluationController(private val evaluationService: EvaluationService,
                           private val dtoService: DtoService) {

    @GetMapping("/evaluations/{evaluationId}")
    fun getEvaluation(@PathVariable evaluationId: Long): EvaluationDTO =
            dtoService.convert(evaluationService.getEvaluation(evaluationId))

    @GetMapping("/evaluations")
    fun getAllEvaluations(): Iterable<EvaluationDTO> = evaluationService.getAll().map { dtoService.convert(it) }

    @PutMapping("/{courseId}/evaluations/{evaluationId}")
    fun updateEvaluation(@PathVariable evaluationId: Long, @RequestBody evaluation: Evaluation) =
            evaluationService.updateEvaluation(evaluationId, evaluation)


    @DeleteMapping("/evaluations/{evaluationId}")
    fun deleteEvaluation(@PathVariable evaluationId: Long) = evaluationService.deleteEvaluation(evaluationId)

}