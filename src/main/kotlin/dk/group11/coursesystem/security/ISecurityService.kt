package dk.group11.coursesystem.security


interface ISecurityService {

    fun getId(): Long

    fun getToken(): String
}