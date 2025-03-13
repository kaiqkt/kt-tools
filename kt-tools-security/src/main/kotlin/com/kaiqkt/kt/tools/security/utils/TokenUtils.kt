package com.kaiqkt.kt.tools.security.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import com.auth0.jwt.interfaces.DecodedJWT
import com.kaiqkt.kt.tools.security.enums.ErrorType
import com.kaiqkt.kt.tools.security.exception.AuthException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

object TokenUtils {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun generateJwt(claims: Map<String, Any>, expiration: Long, secret: String): String {
        val jwtBuilder = JWT.create()
            .withIssuer("kaiqkt")
            .withExpiresAt(LocalDateTime.now().plusMinutes(expiration).atZone(ZoneId.systemDefault()).toInstant())

        claims.forEach { (key, value) ->
            when (value) {
                is String -> jwtBuilder.withClaim(key, value)
                is Int -> jwtBuilder.withClaim(key, value)
                is Long -> jwtBuilder.withClaim(key, value)
                is Boolean -> jwtBuilder.withClaim(key, value)
                is Double -> jwtBuilder.withClaim(key, value)
                is List<*> -> jwtBuilder.withArrayClaim(key, value.filterIsInstance<String>().toTypedArray())
                else -> throw IllegalArgumentException("Unsupported claim type for key: $key")
            }
        }

        return jwtBuilder.sign(Algorithm.HMAC256(secret))
    }

    fun generateHash(data: String, secret: String): String {
        val algorithm = Algorithm.HMAC256(secret)
        val hash = algorithm.sign(data.toByteArray())

        return Base64.getEncoder().encodeToString(hash)
    }

    fun verifyHash(token: String, data: String, secret: String): Boolean {
        return token == generateHash(data, secret)
    }

    fun verifyJwt(token: String, secret: String): DecodedJWT {
        val algorithm = Algorithm.HMAC256(secret)
        val verifier = JWT.require(algorithm)
            .build()

        return try {
            verifier.verify(token)
        } catch (e: TokenExpiredException) {
            log.error("Token expired: {}", e.message)
            throw AuthException("Token Expired", ErrorType.TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED)
        } catch (ex: JWTVerificationException) {
            throw AuthException("Invalid token", ErrorType.INVALID_TOKEN, HttpStatus.UNAUTHORIZED)
        }
    }

    fun extractJwtClaims(token: String, secret: String): Map<String, Any> {
        val decodedJwt = verifyJwt(token, secret)
        val claims = mutableMapOf<String, Any>()

        decodedJwt.claims.forEach { (key, claim) ->
            when {
                claim.asString() != null -> claims[key] = claim.asString()
                claim.asInt() != null -> claims[key] = claim.asInt()
                claim.asLong() != null -> claims[key] = claim.asLong()
                claim.asBoolean() != null -> claims[key] = claim.asBoolean()
                claim.asDouble() != null -> claims[key] = claim.asDouble()
                claim.asList(String::class.java) != null -> claims[key] = claim.asList(String::class.java)
                else -> throw IllegalArgumentException("Unsupported claim type for key: $key")
            }
        }

        return claims
    }
}
