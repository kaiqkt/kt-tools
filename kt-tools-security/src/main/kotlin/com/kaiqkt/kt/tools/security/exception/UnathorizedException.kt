package com.kaiqkt.kt.tools.security.exception

import com.kaiqkt.kt.tools.security.enums.ErrorType
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException

class AuthException(
    message: String,
    val type: ErrorType,
    val status: HttpStatus
) : AuthenticationException(message)
