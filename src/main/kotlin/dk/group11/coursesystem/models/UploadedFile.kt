package dk.group11.coursesystem.models

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class UploadedFile(
        @Id
        val id: Long
)