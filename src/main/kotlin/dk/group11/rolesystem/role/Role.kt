package dk.group11.rolesystem.role

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Role(var title: String = "",
                var key: String = "",
                @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long = 0)