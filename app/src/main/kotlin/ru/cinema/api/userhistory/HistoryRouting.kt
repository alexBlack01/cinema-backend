package ru.cinema.api.userhistory

import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.resources.get
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject
import ru.cinema.api.common.extensions.principalOrThrow
import ru.cinema.api.common.extensions.respondSuccess
import ru.cinema.api.userhistory.controller.HistoryController
import ru.cinema.api.userhistory.model.HistoryResponse
import ru.cinema.api.userhistory.route.UserHistory
import ru.cinema.auth.model.AuthConstants
import ru.cinema.auth.model.UserPrincipal

fun Route.configureHistoryRouting() {
    val controller by inject<HistoryController>()

    authenticate(AuthConstants.USER_AUTH) {
        get<UserHistory> {
            val userId = call.principalOrThrow<UserPrincipal>().userId

            call.respondSuccess(
                controller.getHistoryByUser(userId).map {
                    HistoryResponse.fromDomain(it)
                }
            )
        }
    }
}
