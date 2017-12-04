package dk.group11.coursesystem.clients

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kittinunf.fuel.Fuel
import dk.group11.coursesystem.models.Activity
import dk.group11.coursesystem.security.HEADER_STRING
import dk.group11.coursesystem.security.ISecurityService
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service

@Service
class CalendarClient(private val calendarConfigProperties: CalendarConfigProperties,
                     private val securityService: ISecurityService) {

    fun getActivity(activityId: Long): Activity {
        val (_, response, result) = Fuel.get("${calendarConfigProperties.url}/api/activities/$activityId")
                .header(Pair("Accepts", "application/json"))
                .header(Pair(HEADER_STRING, securityService.getToken()))
                .responseString()
        // TODO proper exception handling



        result.fold(
                {
                    return ObjectMapper().readValue(it, Activity::class.java)
                },
                {
                    System.err.println(it)
                    println(response)
                    return Activity()
                })
    }

    fun createActivity(activity: Activity): Activity {
        val (_, _, result) = Fuel.post(path = "${calendarConfigProperties.url}/api/activities")
                .header(Pair(HEADER_STRING, securityService.getToken()))
                .header(Pair("Content-Type", "application/json"))
                .body(ObjectMapper().writeValueAsBytes(activity))
                .responseString()

        result.fold(
                { return ObjectMapper().readValue(it, Activity::class.java) },
                {
                    System.err.println(it)
                    return Activity()
                }
        )
    }

    fun updateActivity(activity: Activity) {
        Fuel.put(path = "${calendarConfigProperties.url}/api/activities")
                .header(Pair(HEADER_STRING, securityService.getToken()))
                .header(Pair("Content-Type", "application/json"))
                .body(ObjectMapper().writeValueAsBytes(activity))
                .responseString()
    }

    fun deleteActivity(activityId: Long) {
        Fuel.delete(path = "${calendarConfigProperties.url}/api/activities/$activityId")
                .header(Pair(HEADER_STRING, securityService.getToken()))
                .header(Pair("Content-Type", "application/json"))
                .responseString()
    }
}


@Configuration
@ConfigurationProperties(prefix = "calender")
data class CalendarConfigProperties(var url: String = "")