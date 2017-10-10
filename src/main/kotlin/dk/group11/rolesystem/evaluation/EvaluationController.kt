package dk.group11.rolesystem.evaluation

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class EvaluationController {

    @Autowired
    val evaluationservice = EvaluationService()

    @GetMapping
    ("/api/courses/{courseid}/evaluations")
    fun getEvaluations(@PathVariable courseid : Long){
        evaluationservice.getEvaluations(courseid)
    }

    @GetMapping
    ("/api/course/{courseid}/evaluation/{evaluationid}")
    fun getEvaluation(@PathVariable courseid: Long, @PathVariable evaluationid : Long){
        evaluationservice.getEvaluation(courseid,evaluationid)
    }

    @PostMapping
    ("/api/course/{courseid}/evaluation/{evaluationid}")
    fun addEvaluation(@RequestBody evaluation : Evaluation){
        evaluationservice.addEvaluation(evaluation)
    }


}