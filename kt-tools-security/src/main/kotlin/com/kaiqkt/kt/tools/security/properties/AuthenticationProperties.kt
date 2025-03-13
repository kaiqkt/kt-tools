package com.kaiqkt.kt.tools.security.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@ConfigurationProperties(prefix = "kt.tools.security.authentication")
@Validated
data class AuthenticationProperties(
    val accessTokenSecret: String,
    val apiKey: String
)
