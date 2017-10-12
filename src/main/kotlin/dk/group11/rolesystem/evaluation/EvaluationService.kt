package dk.group11.rolesystem.evaluation

import org.springframework.stereotype.Service

@Service
class EvaluationService {
    //@Autowired
    //lateinit var evaluationrepository : Evaluation

    fun getEvaluations(id : Long){
        //  evaluationrepository.findByCourseId(id)
    }
    fun getEvaluation(courseid : Long, evaluationid : Long){
        //evaluationrepository.findByCourseIdAndEvaluationId(courseid,evaluationid)
    }
    fun addEvaluation(evaluation: Evaluation){
        //evaluationrepository.save(evaluation)
    }
}