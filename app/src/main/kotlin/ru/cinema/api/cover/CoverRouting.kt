package ru.cinema.api.cover

import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.resources.get
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject
import ru.cinema.api.common.extensions.respondSuccess
import ru.cinema.api.cover.controller.CoverController
import ru.cinema.api.cover.model.CoverResponse
import ru.cinema.api.cover.route.Cover
import ru.cinema.auth.model.AuthConstants

fun Route.configureCoverRouting() {
    val controller by inject<CoverController>()

    authenticate(AuthConstants.USER_AUTH) {
        get<Cover> {
            val cover = controller.getCover()
            call.respondSuccess(
                CoverResponse.fromDomain(cover)
            )
        }
    }
}
