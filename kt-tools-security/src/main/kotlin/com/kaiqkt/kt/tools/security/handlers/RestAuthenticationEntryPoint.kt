package com.kaiqkt.kt.tools.security.handlers

import com.kaiqkt.kt.tools.security.dtos.Error
import com.kaiqkt.kt.tools.security.enums.ErrorType
import com.kaiqkt.kt.tools.security.exception.AuthException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component("restAuthenticationEntryPoint")
class RestAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        val message = authException?.message ?: "Authentication Error"
        val errorType = ErrorType.AUTHENTICATION_ERROR
        val error = Error(errorType, message)

        if (authException is AuthException) {
            response?.status = authException.status.value()
            response?.contentType = "application/json"
            response?.writer?.write(error.toString())
        }
    }

}
