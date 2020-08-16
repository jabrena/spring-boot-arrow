import com.jab.microservices.app
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import java.net.URL

class IntegrationTests {

    private val client = WebTestClient.bindToServer()
        .baseUrl("http://localhost:8181")
        .build()

    private lateinit var context: ConfigurableApplicationContext

    @BeforeEach
    fun beforeAll() {
        context = app.run(profiles = "test")
    }

    @Disabled
    @Test
    fun `Given Handler When call endpoint api Then Ok`() {
        client.get()
            .uri("/api")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .exchange()
            .expectStatus().isOk
            .expectBody<String>().isEqualTo("Hello world!")
    }

    @Test
    fun `Simple Test` () {

        var response = URL("https://localhost:8181").readText();
        then(response).isEqualTo("Hello World")
    }

    @BeforeEach
    fun afterAll() {
        context.close()
    }
}
