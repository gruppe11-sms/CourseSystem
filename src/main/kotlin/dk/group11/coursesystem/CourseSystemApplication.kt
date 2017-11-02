package dk.group11.coursesystem

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class CourseSystemApplication

fun main(args: Array<String>) {
    SpringApplication.run(CourseSystemApplication::class.java, *args)
}
