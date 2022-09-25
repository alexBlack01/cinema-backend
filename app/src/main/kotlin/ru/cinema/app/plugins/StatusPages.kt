package ru.cinema.app.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.statuspages.StatusPages
import kotlinx.serialization.SerializationException
import ru.cinema.api.common.error.BadRequestFailure
import ru.cinema.api.common.extensions.respondError
import ru.cinema.domain.common.error.InternalServerFailure
import ru.cinema.domain.common.error.NotFoundFailure

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<BadRequestFailure> { call, cause ->
            call.respondError(
                status = HttpStatusCode.BadRequest,
                errorCode = cause.code,
                message = cause.message
            )
        }

        exception<NotFoundFailure> { call, cause ->
            call.respondError(
                status = HttpStatusCode.BadRequest,
                errorCode = cause.code,
                message = cause.message
            )
        }

        exception<BadRequestException> { call, cause ->
            call.respondError(
                status = HttpStatusCode.BadRequest,
                errorCode = BadRequestFailure::class.java.simpleName,
                message = cause.cause?.message ?: cause.message
            )
        }

        exception<SerializationException> { call, cause ->
            call.respondError(
                status = HttpStatusCode.BadRequest,
                errorCode = BadRequestFailure::class.java.simpleName,
                message = cause.message
            )
        }

        exception<Exception> { call, cause ->
            call.respondError(
                status = HttpStatusCode.InternalServerError,
                errorCode = InternalServerFailure::class.java.simpleName,
                message = cause.message
            )
        }
    }
}
