package com.kaiqkt.kt.tools.security.utils

import com.kaiqkt.kt.tools.security.dtos.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder

object ContextUtils {
    fun <T> getValue(key: String, type: Class<T>): T? {
        val context: SecurityContext = SecurityContextHolder.getContext()
        val authentication: Authentication = context.authentication as Authentication
        val value = (authentication.details as Map<*, *>)[key]

        if (value == null) {
            return null
        }

        return type.cast(value)
    }
}
