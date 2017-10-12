package dk.group11.rolesystem.course

import org.springframework.web.bind.annotation.*

@RestController
class CourseController(val courseService: CourseService) {

    @GetMapping("/api")
    fun sayHello(): String {
        return "Hello"
    }

    @GetMapping("/api/courses/{id}")
    fun getCourseById(@PathVariable id: Long): Course {
        return courseService.getCourseById(id)
    }

    @GetMapping("/api/courses")
    fun getCourses(): Iterable<Course> {
        return courseService.getCourses()
    }

    @PutMapping("/api/courses")
    fun updateCourse(@RequestBody course : Course) {
        return courseService.updateCourse(course)
    }

    @PostMapping("/api/courses/{id}/participant")
    fun saveParticipant(@PathVariable id: Long, @RequestBody participant: Participant) {
        courseService.saveParticipant(id, participant)
    }

    @DeleteMapping("/api/courses/{id}")
    fun deleteCourse(id : Long){
        return courseService.deleteCourse(id)
    }


}