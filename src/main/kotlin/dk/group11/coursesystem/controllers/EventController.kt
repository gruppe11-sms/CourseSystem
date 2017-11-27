package dk.group11.coursesystem.controllers

import dk.group11.coursesystem.services.DtoService
import dk.group11.coursesystem.services.EventService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/events")
class EventController(private val eventService: EventService,
                      private val dtoService: DtoService) {

    @GetMapping
    fun getEvents(): List<SimpleEventDTO> = eventService.getEvents().map { dtoService.convert(it) }
}

