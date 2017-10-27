package dk.group11.rolesystem.Controllers

import dk.group11.rolesystem.Models.Course
import dk.group11.rolesystem.Models.Evaluation
import dk.group11.rolesystem.Services.CourseService
import dk.group11.rolesystem.Services.EvaluationService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/courses")
class CourseEvaluationController(private val evaluationService: EvaluationService,
                                 private val courseService: CourseService) {

    @GetMapping("/{courseId}/evaluations")
    fun getCourseEvaluations(@PathVariable courseId: Long): MutableList<Evaluation> {
        return courseService.getCourseById(courseId).courseEvaluations
    }

    @PostMapping("/{courseId}/evaluations")
    fun addCourseEvaluation(@PathVariable courseId: Long, @RequestBody evaluation: Evaluation) {
        val course: Course = courseService.getCourseById(courseId)
        course.courseEvaluations.add(evaluation)
        courseService.saveCourse(course)
    }
}