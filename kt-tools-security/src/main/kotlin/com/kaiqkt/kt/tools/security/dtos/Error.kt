package com.kaiqkt.kt.tools.security.dtos

import com.kaiqkt.kt.tools.security.enums.ErrorType

data class Error(
    val type: ErrorType,
    val message: String? = null
) {
    override fun toString(): String {
        return """
                {
                    "type": "%s",
                    "message": "%s"
                }
                """.trimIndent().format(type, message)
    }
}
