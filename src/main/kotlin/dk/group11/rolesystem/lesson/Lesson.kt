package dk.group11.rolesystem.lesson

import dk.group11.rolesystem.activity.Activity
import dk.group11.rolesystem.room.Room
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
data class Lesson(@OneToMany var room: List<Room> = ArrayList<Room>()) : Activity()