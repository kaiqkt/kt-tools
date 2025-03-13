package com.kaiqkt.kt.tools.security.dtos

import com.kaiqkt.kt.tools.security.enums.DefaultRoles
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.stream.Collectors

class Authentication : org.springframework.security.core.Authentication {
    private var data: Map<String, Any>? = null
    private var authenticated = true
    private var token: String? = null


    constructor(token: String, data: Map<String, Any>) : super() {
        this.token = token
        this.data = data
    }


    override fun getAuthorities(): Collection<GrantedAuthority> {
        val roles = data?.get("roles")

        if (roles != null && roles is List<*>) {
            return roles.stream()
                .filter { role -> role is String }
                .map { role -> SimpleGrantedAuthority(role as String) }
                .collect(Collectors.toList())
        }

        return listOf(SimpleGrantedAuthority(DefaultRoles.ROLE_PUBLIC.name))
    }

    override fun getCredentials(): Any? {
        return token
    }

    override fun getDetails(): Any? {
        return data
    }

    override fun getPrincipal(): Any? {
        return data?.get("user_id") ?: "anonymous"
    }

    override fun isAuthenticated(): Boolean = authenticated

    override fun setAuthenticated(isAuthenticated: Boolean) {
        authenticated = isAuthenticated
    }

    override fun getName(): String?  = "obfuscated"

}
