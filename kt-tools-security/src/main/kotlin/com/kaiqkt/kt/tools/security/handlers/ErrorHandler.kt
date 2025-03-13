package com.kaiqkt.kt.tools.security.handlers

import com.kaiqkt.kt.tools.security.dtos.Error
import com.kaiqkt.kt.tools.security.enums.ErrorType
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandlerAdvice {
    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(e: AccessDeniedException?): ResponseEntity<*> {
        val error = Error(ErrorType.ACCESS_DENIED, "You are not authorized to view this resource.")
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error)
    }
}
