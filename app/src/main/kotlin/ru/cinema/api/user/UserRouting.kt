package ru.cinema.api.user

import io.ktor.server.application.application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.resources.get
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject
import ru.cinema.api.common.extensions.getBaseUrl
import ru.cinema.api.common.extensions.principalOrThrow
import ru.cinema.api.common.extensions.respondSuccess
import ru.cinema.api.user.controller.UserController
import ru.cinema.api.user.model.UserResponse
import ru.cinema.api.user.route.Profile
import ru.cinema.auth.model.UserPrincipal

fun Route.configureUserRouting() {
    val controller by inject<UserController>()

    authenticate {
        get<Profile> {
            val userId = call.principalOrThrow<UserPrincipal>().userId

            val userProfile = controller.getUserProfile(userId)
            call.respondSuccess(UserResponse.fromDomain(data = userProfile, baseUrl = application.getBaseUrl()))
        }
    }
}
