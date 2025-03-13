package com.kaiqkt.kt.tools.security.handlers

import com.kaiqkt.kt.tools.security.dtos.Authentication
import com.kaiqkt.kt.tools.security.enums.DefaultRoles
import com.kaiqkt.kt.tools.security.enums.ErrorType
import com.kaiqkt.kt.tools.security.exception.AuthException
import com.kaiqkt.kt.tools.security.properties.AuthenticationProperties
import com.kaiqkt.kt.tools.security.utils.TokenUtils
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
@EnableConfigurationProperties(AuthenticationProperties::class)
class AuthenticationHandler(private val properties: AuthenticationProperties) {

    fun handleAccessToken(token: String): Authentication {
        return Authentication(token, TokenUtils.extractJwtClaims(token, properties.accessTokenSecret))
    }

    fun handleApiKey(apiKey: String): Authentication {
        if (apiKey == properties.apiKey) {
            val data: Map<String, Any> = mapOf("roles" to listOf(DefaultRoles.ROLE_API.name))

            return Authentication("obfuscated", data)
        }

        throw AuthException("Invalid Api Key", ErrorType.INVALID_TOKEN, HttpStatus.UNAUTHORIZED)
    }

    fun handlePublicAccess(): Authentication {
        val data: Map<String, Any> = mapOf("roles" to listOf(DefaultRoles.ROLE_PUBLIC.name))

        return Authentication("public_access", data)
    }
}
