package ru.cinema.api.preference

import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.resources.get
import io.ktor.server.resources.put
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject
import ru.cinema.api.common.extensions.principalOrThrow
import ru.cinema.api.common.extensions.respondSuccess
import ru.cinema.api.common.extensions.respondSuccessNoContent
import ru.cinema.api.preference.controller.PreferenceController
import ru.cinema.api.preference.model.PreferenceEditBody
import ru.cinema.api.preference.route.Preferences
import ru.cinema.api.tag.model.TagResponse
import ru.cinema.auth.model.AuthConstants
import ru.cinema.auth.model.UserPrincipal

fun Route.configurePreferenceRouting() {
    val controller by inject<PreferenceController>()

    authenticate(AuthConstants.USER_AUTH) {
        get<Preferences> {
            val userId = call.principalOrThrow<UserPrincipal>().userId

            call.respondSuccess(controller.getPreferencesByUser(userId).map { TagResponse.fromDomain(it) })
        }

        put<Preferences> {
            val userId = call.principalOrThrow<UserPrincipal>().userId
            val body = call.receive<List<PreferenceEditBody>>()

            controller.putPreferencesByUser(userId, body)
            call.respondSuccessNoContent()
        }
    }
}
