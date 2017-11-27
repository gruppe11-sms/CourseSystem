package dk.group11.coursesystem.repositories

import dk.group11.coursesystem.models.Evaluation
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EvaluationRepository : CrudRepository<Evaluation, Long>