package com.kaiqkt.kt.tools.security.configs

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.PropertySource

@Configuration(proxyBeanMethods = false)
@PropertySource("classpath:security.properties")
@Import(value = [WebSecurityConfig::class])
class SecurityAutoConfig
