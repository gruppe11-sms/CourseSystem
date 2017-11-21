package dk.group11.coursesystem.repositories

import dk.group11.coursesystem.models.Event
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EventRepository : CrudRepository<Event, Long> {
}