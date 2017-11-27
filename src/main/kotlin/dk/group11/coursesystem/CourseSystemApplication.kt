package dk.group11.coursesystem

import dk.group11.coursesystem.clients.RoleClient
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.retry.annotation.EnableRetry
import org.springframework.stereotype.Component

@SpringBootApplication
@EnableRetry
class CourseSystemApplication

fun main(args: Array<String>) {
    SpringApplication.run(CourseSystemApplication::class.java, *args)
}

@Component
class Startup(private val roleClient: RoleClient) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        roleClient.ensureRole(COURSE_MANAGEMENT_ROLE, "Course Manager", "Allows the user to create, edit and delete courses")
    }

}