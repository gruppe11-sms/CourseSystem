package dk.group11.rolesystem.Models

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
data class Lesson(
        @OneToMany(cascade = arrayOf(CascadeType.ALL))
        var room: MutableList<Room> = mutableListOf()
) : Activity()