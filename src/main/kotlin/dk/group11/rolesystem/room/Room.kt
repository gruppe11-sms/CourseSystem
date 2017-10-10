package dk.group11.rolesystem.room

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Room(
            var name: String = "",
            var roomnr: Int = 0,
            @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long = 0)