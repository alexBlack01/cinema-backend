package ru.cinema.api.episodetime

import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject
import ru.cinema.api.common.extensions.principalOrThrow
import ru.cinema.api.common.extensions.respondSuccess
import ru.cinema.api.common.extensions.respondSuccessCreated
import ru.cinema.api.episodetime.controller.EpisodeTimeController
import ru.cinema.api.episodetime.model.EpisodeTimeBody
import ru.cinema.api.episodetime.model.EpisodeTimeResponse
import ru.cinema.api.episodetime.route.EpisodeTime
import ru.cinema.auth.model.AuthConstants
import ru.cinema.auth.model.UserPrincipal

fun Route.configureEpisodeTimesRouting() {
    val controller by inject<EpisodeTimeController>()

    authenticate(AuthConstants.USER_AUTH) {
        get<EpisodeTime> { param ->
            val userId = call.principalOrThrow<UserPrincipal>().userId

            val episodeTime = controller.getEpisodeTimeByUser(userId, param.episodeId)
            call.respondSuccess(EpisodeTimeResponse.fromDomain(episodeTime))
        }

        post<EpisodeTime> { param ->
            val userId = call.principalOrThrow<UserPrincipal>().userId

            val body = call.receive<EpisodeTimeBody>()

            controller.postEpisodeTimeByUser(userId, param.episodeId, body)
            call.respondSuccessCreated()
        }
    }
}
