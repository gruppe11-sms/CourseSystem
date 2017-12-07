package dk.group11.coursesystem.repositories

import dk.group11.coursesystem.models.HandInAssignment
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface HandInRepository : CrudRepository<HandInAssignment, Long>