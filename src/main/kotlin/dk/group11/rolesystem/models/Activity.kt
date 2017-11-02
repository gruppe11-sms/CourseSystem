package dk.group11.rolesystem.models

import java.util.*
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class Activity {
    abstract val title: String
    val startdate: Date = Date()
    val enddate: Date = Date()
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0
}