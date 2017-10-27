package dk.group11.rolesystem.Models

import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
data class Lesson(
        @ManyToOne
        @JsonBackReference
        var course: Course = Course(),

        override val title: String = "",

        @OneToMany(mappedBy = "lesson", cascade = arrayOf(CascadeType.ALL))
        var room: MutableList<Room> = mutableListOf()
) : Activity()