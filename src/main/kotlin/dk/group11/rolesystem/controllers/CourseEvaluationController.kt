package dk.group11.rolesystem.controllers

import dk.group11.rolesystem.models.Course
import dk.group11.rolesystem.models.Evaluation
import dk.group11.rolesystem.services.CourseService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/courses")
class CourseEvaluationController(private val courseService: CourseService) {

    @GetMapping("/{courseId}/evaluations")
    fun getCourseEvaluations(@PathVariable courseId: Long): Iterable<EvaluationDTO> {
        return courseService.getCourseById(courseId).evaluations.map { it.toDTO() }
    }

    @PostMapping("/{courseId}/evaluations")
    fun addCourseEvaluation(@PathVariable courseId: Long, @RequestBody evaluation: Evaluation) {
        val course: Course = courseService.getCourseById(courseId)
        course.evaluations.add(evaluation)
        courseService.saveCourse(course)
    }
}