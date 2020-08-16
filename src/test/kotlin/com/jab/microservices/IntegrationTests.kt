package com.jab.microservices

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

class IntegrationTests {

    private val client = WebTestClient.bindToServer()
        .baseUrl("http://localhost:8181")
        .build()

    private lateinit var context: ConfigurableApplicationContext

    @BeforeEach
    fun beforeAll() {
        context = app.run(profiles = "test")
    }

    @Test
    fun `Given Handler When call endpoint api Then Ok`() {
        client.get()
            .uri("/api")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .exchange()
            .expectStatus().isOk
            .expectBody<String>().isEqualTo("{\"message\":\"Hello World\"}")
    }

    @AfterEach
    fun afterAll() {
        context.close()
    }
}
