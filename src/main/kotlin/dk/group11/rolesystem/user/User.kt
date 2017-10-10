package dk.group11.rolesystem.user

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

data class User(@Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long = 0) {
}