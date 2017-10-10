package dk.group11.rolesystem.evaluation

import dk.group11.rolesystem.user.User
import javax.persistence.Entity

@Entity
data class Evaluation(
        var grade : String ="",
        var feedback : String ="",
        var evaluator : User = User(),
        var evaluee : User = User()
        //file
        ){

}
