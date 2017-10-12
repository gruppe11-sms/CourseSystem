package dk.group11.rolesystem.activity

import java.util.*
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
open class Activity {
    val title: String = ""
    val startdate: Date = Date()
    val enddate: Date = Date()
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0
}