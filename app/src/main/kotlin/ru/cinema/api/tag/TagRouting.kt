package ru.cinema.api.tag

import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.resources.patch
import io.ktor.server.resources.post
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject
import ru.cinema.api.common.extensions.admin
import ru.cinema.api.common.extensions.respondSuccess
import ru.cinema.api.common.extensions.respondSuccessNoContent
import ru.cinema.api.common.extensions.user
import ru.cinema.api.common.routing.RoutingRoleVerifier.verifyRoles
import ru.cinema.api.tag.controller.TagController
import ru.cinema.api.tag.model.TagBody
import ru.cinema.api.tag.model.TagEditBody
import ru.cinema.api.tag.model.TagResponse
import ru.cinema.api.tag.route.NewTag
import ru.cinema.api.tag.route.Tags
import ru.cinema.api.tag.route.UpdateTag
import ru.cinema.auth.model.AuthConstants
import ru.cinema.domain.tag.model.Tag
import ru.cinema.domain.user.roles.model.UserRole
import java.util.UUID

fun Route.configureTagsRouting() {
    val controller by inject<TagController>()

    tagUser(
        getAllTagsRequest = controller::getAllTags
    )
    tagAdmin(
        createTagRequest = controller::postNewTag,
        patchTagInfoRequest = controller::patchTagById,
        deleteTagRequest = controller::deleteTagById
    )
}

private fun Route.tagUser(
    getAllTagsRequest: suspend () -> List<Tag>
) = user {
    authenticate(AuthConstants.USER_AUTH) {
        get<Tags> {
            call.respondSuccess(getAllTagsRequest.invoke().map { TagResponse.fromDomain(it) })
        }
    }
}

private fun Route.tagAdmin(
    createTagRequest: suspend (TagBody) -> Tag,
    patchTagInfoRequest: suspend (UUID, TagEditBody) -> Tag,
    deleteTagRequest: suspend (UUID) -> Unit
) = admin {
    authenticate(AuthConstants.ADMIN_AUTH) {
        post<NewTag> {
            verifyRoles(setOf(UserRole.ADMIN))
            val body = call.receive<TagBody>()

            val tag = createTagRequest.invoke(body)
            call.respondSuccess(
                TagResponse.fromDomain(tag)
            )
        }

        patch<UpdateTag> { param ->
            verifyRoles(setOf(UserRole.ADMIN))
            val body = call.receive<TagEditBody>()

            val tag = patchTagInfoRequest.invoke(param.tagId, body)
            call.respondSuccess(
                TagResponse.fromDomain(tag)
            )
        }

        delete<UpdateTag> { param ->
            verifyRoles(setOf(UserRole.ADMIN))

            deleteTagRequest.invoke(param.tagId)
            call.respondSuccessNoContent()
        }
    }
}
