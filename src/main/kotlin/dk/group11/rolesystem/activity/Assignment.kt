package dk.group11.rolesystem.activity

import dk.group11.rolesystem.course.Course
import dk.group11.rolesystem.evaluation.Evaluation
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

data class Assignment  (
        var description: String = "",
        @OneToMany
        var evaluation : List<Evaluation> = ArrayList<Evaluation>()
) : Activity()
