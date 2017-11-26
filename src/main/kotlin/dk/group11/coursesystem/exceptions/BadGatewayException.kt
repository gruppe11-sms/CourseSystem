package dk.group11.coursesystem.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_GATEWAY)
class BadGatewayException(message: String) : RuntimeException(message)