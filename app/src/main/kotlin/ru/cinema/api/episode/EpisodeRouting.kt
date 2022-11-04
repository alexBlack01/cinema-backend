package ru.cinema.api.episode

import io.ktor.http.content.MultiPartData
import io.ktor.server.application.application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.request.receiveMultipart
import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.resources.patch
import io.ktor.server.resources.post
import io.ktor.server.resources.put
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject
import ru.cinema.api.common.extensions.admin
import ru.cinema.api.common.extensions.getBaseUrl
import ru.cinema.api.common.extensions.respondSuccess
import ru.cinema.api.common.extensions.respondSuccessNoContent
import ru.cinema.api.common.extensions.user
import ru.cinema.api.common.routing.RoutingRoleVerifier.verifyRoles
import ru.cinema.api.episode.controller.EpisodeController
import ru.cinema.api.episode.model.EpisodeBody
import ru.cinema.api.episode.model.EpisodeEditBody
import ru.cinema.api.episode.model.EpisodeResponse
import ru.cinema.api.episode.route.EpisodeFile
import ru.cinema.api.episode.route.Episodes
import ru.cinema.api.episode.route.NewEpisode
import ru.cinema.api.episode.route.UpdateEpisode
import ru.cinema.app.common.utils.SystemEnvVariablesUtil
import ru.cinema.auth.model.AuthConstants
import ru.cinema.domain.episode.model.Episode
import ru.cinema.domain.user.roles.model.UserRole
import java.util.*

fun Route.configureEpisodesRouting() {
    val controller by inject<EpisodeController>()

    episodeUser(
        getEpisodesByMovie = controller::getEpisodesByMovie
    )
    episodeAdmin(
        createEpisodeRequest = controller::postNewEpisode,
        putEpisodeContentRequest = controller::insertFilesForEpisode,
        patchEpisodeInfoRequest = controller::patchEpisodeById,
        deleteEpisodeRequest = controller::deleteEpisodeById
    )
}

private fun Route.episodeUser(
    getEpisodesByMovie: suspend (UUID) -> List<Episode>
) = user {
    authenticate(AuthConstants.USER_AUTH) {
        get<Episodes> { params ->
            call.respondSuccess(
                getEpisodesByMovie.invoke(
                    params.movieId
                ).map {
                    EpisodeResponse.fromDomain(
                        data = it,
                        baseUrl = application.getBaseUrl(),
                        uploadFolder = SystemEnvVariablesUtil.uploadFolder,
                        episodeImageFolder = SystemEnvVariablesUtil.episodeImageFolder,
                        previewFolder = SystemEnvVariablesUtil.previewFolder,
                        fileFolder = SystemEnvVariablesUtil.fileFolder
                    )
                }
            )
        }
    }
}

private fun Route.episodeAdmin(
    createEpisodeRequest: suspend (UUID, EpisodeBody) -> Episode,
    putEpisodeContentRequest: suspend (UUID, UUID, MultiPartData) -> Unit,
    patchEpisodeInfoRequest: suspend (UUID, UUID, EpisodeEditBody) -> Episode,
    deleteEpisodeRequest: suspend (UUID, UUID) -> Unit
) = admin {
    authenticate(AuthConstants.ADMIN_AUTH) {
        post<NewEpisode> { param ->
            verifyRoles(setOf(UserRole.ADMIN))
            val body = call.receive<EpisodeBody>()

            val episode = createEpisodeRequest.invoke(param.movieId, body)
            call.respondSuccess(
                EpisodeResponse.fromDomain(
                    data = episode,
                    baseUrl = application.getBaseUrl(),
                    uploadFolder = SystemEnvVariablesUtil.uploadFolder,
                    episodeImageFolder = SystemEnvVariablesUtil.episodeImageFolder,
                    previewFolder = SystemEnvVariablesUtil.previewFolder,
                    fileFolder = SystemEnvVariablesUtil.fileFolder
                )
            )
        }

        put<EpisodeFile> { param ->
            verifyRoles(setOf(UserRole.ADMIN))

            val files = call.receiveMultipart()
            putEpisodeContentRequest.invoke(param.movieId, param.episodeId, files)
            call.respondSuccessNoContent()
        }

        patch<UpdateEpisode> { param ->
            verifyRoles(setOf(UserRole.ADMIN))
            val body = call.receive<EpisodeEditBody>()

            val episode = patchEpisodeInfoRequest.invoke(param.movieId, param.episodeId, body)
            call.respondSuccess(
                EpisodeResponse.fromDomain(
                    data = episode,
                    baseUrl = application.getBaseUrl(),
                    uploadFolder = SystemEnvVariablesUtil.uploadFolder,
                    episodeImageFolder = SystemEnvVariablesUtil.episodeImageFolder,
                    previewFolder = SystemEnvVariablesUtil.previewFolder,
                    fileFolder = SystemEnvVariablesUtil.fileFolder
                )
            )
        }

        delete<UpdateEpisode> { param ->
            verifyRoles(setOf(UserRole.ADMIN))

            deleteEpisodeRequest.invoke(param.movieId, param.episodeId)
            call.respondSuccessNoContent()
        }
    }
}
