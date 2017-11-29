package dk.group11.coursesystem.models

import dk.group11.coursesystem.controllers.SimpleEventDTO
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Event(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0,
        var activityId: Long = 0,
        val description: String = ""
) {
        fun toDTO(): SimpleEventDTO {
                return SimpleEventDTO(activityId = activityId, description = description, id = id)
        }
}
