package dk.group11.coursesystem.models

import java.util.*

data class Activity(val id: Long = 0,
                    val title: String = "",
                    val startDate: Date = Date(),
                    val endDate: Date = Date())