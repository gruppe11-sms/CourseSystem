package dk.group11.coursesystem.services

import dk.group11.coursesystem.models.Event
import dk.group11.coursesystem.repositories.EventRepository
import org.springframework.stereotype.Service

@Service
class EventService(private val eventRepository : EventRepository) {

    fun getEvents(): List<Event> {
        return eventRepository.findAll().toList()
    }

}

