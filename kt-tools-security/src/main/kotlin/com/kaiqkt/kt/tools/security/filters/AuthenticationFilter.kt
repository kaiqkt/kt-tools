package com.kaiqkt.kt.tools.security.filters

import com.kaiqkt.kt.tools.security.handlers.AuthenticationHandler
import com.kaiqkt.kt.tools.security.handlers.RestAuthenticationEntryPoint
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class AuthenticationFilter(
    private val authenticationHandler: AuthenticationHandler,
    private val authenticationEntryPoint: RestAuthenticationEntryPoint
) : OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val token = request.getHeader(HttpHeaders.AUTHORIZATION).orEmpty()

            val authentication = token.takeIf { it.isNotEmpty() }?.let {
                when {
                    it.startsWith("Bearer ") -> authenticationHandler.handleAccessToken(it.removePrefix("Bearer "))
                    else -> authenticationHandler.handleAccessToken(it)
                }
            } ?: authenticationHandler.handlePublicAccess()

            SecurityContextHolder.getContext().authentication = authentication
            filterChain.doFilter(request, response)
        } catch (ex: AuthenticationException) {
            SecurityContextHolder.clearContext()
            authenticationEntryPoint.commence(request, response, ex)
        }
    }
}
