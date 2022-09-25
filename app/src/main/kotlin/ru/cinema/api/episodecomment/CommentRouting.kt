package ru.cinema.api.episodecomment

import io.ktor.server.application.application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.routing.Routing
import org.koin.ktor.ext.inject
import ru.cinema.api.common.extensions.getBaseUrl
import ru.cinema.api.common.extensions.principalOrThrow
import ru.cinema.api.common.extensions.respondSuccess
import ru.cinema.api.episodecomment.controller.CommentController
import ru.cinema.api.episodecomment.model.CommentFormBody
import ru.cinema.api.episodecomment.model.CommentFormText
import ru.cinema.api.episodecomment.model.CommentResponse
import ru.cinema.api.episodecomment.route.Comments
import ru.cinema.api.episodecomment.route.PostComment
import ru.cinema.auth.model.UserPrincipal

fun Routing.configureCommentsRouting() {
    val controller by inject<CommentController>()

    authenticate {
        get<Comments> { param ->
            call.respondSuccess(
                controller.getCommentsByEpisode(
                    param.episodeId
                ).map { CommentResponse.fromDomain(data = it, baseUrl = application.getBaseUrl()) }
            )
        }

        post<PostComment> { param ->
            val userId = call.principalOrThrow<UserPrincipal>().userId
            val textComment = call.receive<CommentFormText>()

            val comment = controller.postCommentByEpisode(
                commentForm = CommentFormBody(
                    userId = userId,
                    episodeId = param.episodeId,
                    text = textComment.text
                )
            )

            call.respondSuccess(CommentResponse.fromDomain(data = comment, baseUrl = application.getBaseUrl()))
        }
    }
}
