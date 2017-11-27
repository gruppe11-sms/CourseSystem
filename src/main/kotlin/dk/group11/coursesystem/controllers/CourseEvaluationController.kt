package dk.group11.coursesystem.controllers

import dk.group11.coursesystem.models.Course
import dk.group11.coursesystem.models.Evaluation
import dk.group11.coursesystem.services.CourseService
import dk.group11.coursesystem.services.DtoService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/courses")
class CourseEvaluationController(private val courseService: CourseService,
                                 private val dtoService: DtoService) {

    @GetMapping("/{courseId}/evaluations")
    fun getCourseEvaluations(@PathVariable courseId: Long): Iterable<EvaluationDTO> =
            courseService.getCourseById(courseId).evaluations.map { dtoService.convert(it) }

    @PostMapping("/{courseId}/evaluations")
    fun addCourseEvaluation(@PathVariable courseId: Long, @RequestBody evaluation: Evaluation) {
        val course: Course = courseService.getCourseById(courseId)
        course.evaluations.add(evaluation)
        courseService.updateCourse(course)
    }
}