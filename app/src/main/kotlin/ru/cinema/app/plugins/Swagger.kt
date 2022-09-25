package ru.cinema.app.plugins

import com.papsign.ktor.openapigen.OpenAPIGen
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.resources.get
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.routing
import ru.cinema.app.docs.GetDocs

fun Application.configureSwagger() {
    val rootPath = environment.rootPath

    val baseSwaggerPath = "${rootPath}swagger/index.html?url=${rootPath}static"

    install(OpenAPIGen) {
        serveSwaggerUi = true
        swaggerUiPath = "${rootPath}swagger"
    }

    routing {
        get<GetDocs> {
            call.respondRedirect(
                url = "$baseSwaggerPath/cinema-api.yaml",
                permanent = true
            )
        }
    }
}
