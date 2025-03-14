package com.kaiqkt.kt.tools.security.configs

import com.kaiqkt.kt.tools.security.filters.AuthenticationFilter
import com.kaiqkt.kt.tools.security.handlers.ErrorHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.intercept.AuthorizationFilter

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
open class WebSecurityConfig(
    private val authenticationFilter: AuthenticationFilter,
    private val exceptionHandler: ErrorHandler
) {

    @Bean
    @Throws(Exception::class)
    open fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity.csrf { it.disable() }
        httpSecurity.cors { it.disable() }
        httpSecurity.sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        httpSecurity.securityMatchers { matchers -> matchers.requestMatchers(*Companion.PATH_MATCHERS) }
        httpSecurity.authorizeHttpRequests { authRequest ->
            authRequest.requestMatchers(*Companion.PATH_MATCHERS).permitAll().anyRequest().authenticated()
        }
        httpSecurity.addFilterBefore(authenticationFilter, AuthorizationFilter::class.java)
        httpSecurity.exceptionHandling { it.accessDeniedHandler(exceptionHandler) }

        return httpSecurity.build()
    }

    object Companion {
        internal val PATH_MATCHERS = arrayOf<String?>(
            "/v2/api-docs",
            "/v3/api-docs/**",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui/index.html",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/webjars/**",
            "/api-docs.yml",
            "/docs",
            "/healthcheck",
            "/metrics"
        )
    }
}
