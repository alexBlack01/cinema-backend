package ru.cinema.api.common.extensions

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.Principal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import ru.cinema.api.common.error.InvalidCredentials
import ru.cinema.api.common.request.ValidatedBody
import ru.cinema.api.common.response.ErrorResponse

suspend fun ApplicationCall.respondError(status: HttpStatusCode, errorCode: String, message: String?) =
    this.respond(status = status, message = ErrorResponse(code = errorCode, message = message))

suspend inline fun <reified T : Any> ApplicationCall.respondSuccess(data: T) =
    this.respond(status = HttpStatusCode.OK, message = data)

suspend fun ApplicationCall.respondSuccessNoContent() =
    this.respond(status = HttpStatusCode.NoContent, message = Unit)

suspend inline fun ApplicationCall.respondSuccessCreated() =
    this.respond(status = HttpStatusCode.Created, message = Unit)

suspend inline fun <reified T : ValidatedBody<*>> ApplicationCall.receiveAndValidate(): T {
    return this.receive<T>().also { it.validate() }
}

inline fun <reified T : Principal> ApplicationCall.principalOrThrow(): T =
    this.principal() ?: throw InvalidCredentials()
