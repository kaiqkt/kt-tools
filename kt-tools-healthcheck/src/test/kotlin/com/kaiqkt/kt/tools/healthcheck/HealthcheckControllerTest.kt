package com.kaiqkt.kt.tools.healthcheck

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.info.BuildProperties
import org.springframework.http.HttpStatus

class HealthcheckControllerTest {
    private val properties: BuildProperties = mockk(relaxed = true)
    private val healthcheckController = HealthcheckController(properties)

    @Test
    fun healthcheck() {
        every { properties.name } returns "kt-tools-healthcheck"
        every { properties.version } returns "1.0.0"

        val result = healthcheckController.healthcheck()

        assertEquals(result.body?.get("application_name"), "kt-tools-healthcheck")
        assertEquals(result.body?.get("application_version"), "1.0.0")
        assertEquals(result.statusCode, HttpStatus.OK)
    }
}
