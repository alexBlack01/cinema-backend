package ru.cinema.api.episodecomment

import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject
import ru.cinema.api.common.extensions.admin
import ru.cinema.api.common.extensions.principalOrThrow
import ru.cinema.api.common.extensions.respondSuccess
import ru.cinema.api.common.extensions.respondSuccessNoContent
import ru.cinema.api.common.extensions.user
import ru.cinema.api.common.routing.RoutingRoleVerifier.verifyRoles
import ru.cinema.api.episodecomment.controller.CommentController
import ru.cinema.api.episodecomment.model.CommentFormBody
import ru.cinema.api.episodecomment.model.CommentFormText
import ru.cinema.api.episodecomment.model.CommentResponse
import ru.cinema.api.episodecomment.route.Comments
import ru.cinema.api.episodecomment.route.DeleteComment
import ru.cinema.api.episodecomment.route.PostComment
import ru.cinema.auth.model.AuthConstants
import ru.cinema.auth.model.UserPrincipal
import ru.cinema.domain.episodecomment.model.Comment
import ru.cinema.domain.user.roles.model.UserRole
import java.util.UUID

fun Route.configureCommentsRouting() {
    val controller by inject<CommentController>()

    commentUser(
        getCommentsByEpisodeRequest = controller::getCommentsByEpisode,
        postCommentByUserRequest = controller::postCommentByEpisode
    )
    commentAdmin(
        deleteCommentRequest = controller::deleteCommentByAdmin
    )
}

private fun Route.commentUser(
    getCommentsByEpisodeRequest: suspend (UUID) -> List<Comment>,
    postCommentByUserRequest: suspend (CommentFormBody) -> Comment
) = user {
    authenticate(AuthConstants.USER_AUTH) {
        get<Comments> { param ->
            call.respondSuccess(
                getCommentsByEpisodeRequest.invoke(
                    param.episodeId
                ).map {
                    CommentResponse.fromDomain(it)
                }
            )
        }

        post<PostComment> { param ->
            val userId = call.principalOrThrow<UserPrincipal>().userId
            val textComment = call.receive<CommentFormText>()

            val comment = postCommentByUserRequest.invoke(
                CommentFormBody(
                    userId = userId,
                    episodeId = param.episodeId,
                    text = textComment.text
                )
            )

            call.respondSuccess(
                CommentResponse.fromDomain(comment)
            )
        }
    }
}

private fun Route.commentAdmin(
    deleteCommentRequest: suspend (UUID, UUID) -> Unit
) = admin {
    authenticate(AuthConstants.ADMIN_AUTH) {
        delete<DeleteComment> { param ->
            verifyRoles(setOf(UserRole.ADMIN))

            deleteCommentRequest.invoke(param.episodeId, param.commentId)
            call.respondSuccessNoContent()
        }
    }
}
