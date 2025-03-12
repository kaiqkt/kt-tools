package com.kaiqkt.kt.tools.healthcheck

import org.springframework.boot.info.BuildProperties
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthcheckController(private val buildProperties: BuildProperties) {

    @GetMapping("/healthcheck")
    fun healthcheck(): ResponseEntity<MutableMap<String, Any>> {
        val data = HashMap<String, Any>()
        data.put("application_name", buildProperties.name)
        data.put("application_version", buildProperties.version)

        return ResponseEntity.ok(data)
    }
}
