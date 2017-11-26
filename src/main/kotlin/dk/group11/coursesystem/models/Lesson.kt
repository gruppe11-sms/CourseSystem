package dk.group11.coursesystem.models

import javax.persistence.*

@Entity
data class Lesson(@Id @GeneratedValue(strategy = GenerationType.AUTO)
                  var id: Long = 0,
                  var activityId: Long = 0,
                  var description: String = "",
                  @ManyToOne
                  var course: Course = Course(),
                  @OneToMany(mappedBy = "lesson", cascade = arrayOf(CascadeType.ALL))
                  var rooms: MutableList<Room> = mutableListOf())