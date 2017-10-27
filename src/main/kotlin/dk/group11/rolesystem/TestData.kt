package dk.group11.rolesystem

import dk.group11.rolesystem.Models.*
import dk.group11.rolesystem.Repositories.*
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import java.util.*
import javax.transaction.Transactional

@Component
class TestData(private val assignmentRepository: AssignmentRepository,
               private val courseRepository: CourseRepository,
               private val evaluationRepository: EvaluationRepository,
               private val lessonRepository: LessonRepository,
               private val participantRepository: ParticipantRepository) : ApplicationRunner {
    @Transactional
    override fun run(args: ApplicationArguments) {
        val testCourses: List<Course> = Arrays.asList(
                Course("matematik", Date(), Date()))

        val testParticipants: List<Participant> = Arrays.asList(
                Participant(0, course = testCourses.first()))

        val testEvaluations: List<Evaluation> = Arrays.asList(
                Evaluation("12", "you did good", course = testCourses.first()))

        val testAssignments: List<Assignment> = Arrays.asList(
                Assignment("Matopgave3","Lav opgave 3 i matbogen", course = testCourses.first(), participant = testParticipants.first()))

        val testLessons: List<Lesson> = Arrays.asList(
                Lesson(title= "Mat Lesson", course = testCourses.first()))

        courseRepository.save(testCourses)
        participantRepository.save(testParticipants)
        evaluationRepository.save(testEvaluations)
        assignmentRepository.save(testAssignments)
        lessonRepository.save(testLessons)

        val course = courseRepository.findByTitle("matematik")
        val participant = participantRepository.findAll()

        course.participant.addAll(participant)
    }
}