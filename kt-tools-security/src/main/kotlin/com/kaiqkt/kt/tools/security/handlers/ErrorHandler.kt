package com.kaiqkt.kt.tools.security.handlers

import com.kaiqkt.kt.tools.security.dtos.Error
import com.kaiqkt.kt.tools.security.enums.ErrorType
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class ErrorHandler() : AccessDeniedHandler {
    override fun handle(request: HttpServletRequest, response: HttpServletResponse, accessDeniedException: AccessDeniedException) {
        val error = Error(ErrorType.ACCESS_DENIED, "You are not authorized to view this resource.")
        response.status = HttpStatus.FORBIDDEN.value()
        response.contentType = "application/json"
        response.writer.write(error.toString())
    }
}
