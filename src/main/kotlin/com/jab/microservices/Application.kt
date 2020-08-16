package com.jab.microservices

import org.springframework.fu.kofu.webApplication
import org.springframework.fu.kofu.webmvc.webMvc
import org.springframework.http.MediaType
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse.ok

val app = webApplication {
    beans {
        bean<SampleHandler>()
    }
    webMvc {
        port = if (profiles.contains("test")) 8181 else 8080
        router {
            val handler = ref<SampleHandler>()
            GET("/api", handler::api)
        }
        converters {
            string()
            jackson()
        }
    }
}

fun main() {
    app.run()
}

data class Sample(val message: String)

class SampleHandler() {
    fun api(request: ServerRequest) = ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(Sample("Hello World"))
}
