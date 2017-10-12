package dk.group11.rolesystem.activity

import javax.persistence.Entity

@Entity
data class Assignment  (
        var description: String = ""
        //@OneToMany
        //var evaluation : List<Evaluation> = ArrayList<Evaluation>()
) : Activity()
