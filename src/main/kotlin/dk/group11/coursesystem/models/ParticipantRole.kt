package dk.group11.coursesystem.models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class ParticipantRole(var title: String = "",
                           var key: String = "",
                           @Id @GeneratedValue(strategy = GenerationType.AUTO)
                           var id: Long = 0)