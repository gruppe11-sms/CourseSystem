package dk.group11.rolesystem.activity

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
open class Activity {
    val title: String = ""
    val startdate: Date = Date()
    val enddate: Date = Date()
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0
}