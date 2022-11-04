package ru.cinema.api.user

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
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject
import ru.cinema.api.common.extensions.admin
import ru.cinema.api.common.extensions.getBaseUrl
import ru.cinema.api.common.extensions.principalOrThrow
import ru.cinema.api.common.extensions.respondSuccess
import ru.cinema.api.common.extensions.respondSuccessCreated
import ru.cinema.api.common.extensions.respondSuccessNoContent
import ru.cinema.api.common.extensions.user
import ru.cinema.api.common.routing.RoutingRoleVerifier.verifyRoles
import ru.cinema.api.user.controller.UserController
import ru.cinema.api.user.model.UserProfileBody
import ru.cinema.api.user.model.UserResponse
import ru.cinema.api.user.route.DeleteProfile
import ru.cinema.api.user.route.PostAvatar
import ru.cinema.api.user.route.Profile
import ru.cinema.app.common.utils.SystemEnvVariablesUtil
import ru.cinema.auth.model.AuthConstants
import ru.cinema.auth.model.UserPrincipal
import ru.cinema.domain.user.model.UserProfile
import ru.cinema.domain.user.roles.model.UserRole
import java.util.UUID

fun Route.configureUserRouting() {
    val controller by inject<UserController>()

    userUser(
        getUserProfileRequest = controller::getUserProfile,
        patchUserProfileRequest = controller::patchUserProfile,
        postUserAvatarRequest = controller::postAvatarByUserId
    )
    userAdmin(
        deleteUserProfileRequest = controller::deleteUserByAdmin
    )
}

private fun Route.userUser(
    getUserProfileRequest: suspend (UUID) -> UserProfile,
    patchUserProfileRequest: suspend (UUID, UserProfileBody) -> UserProfile,
    postUserAvatarRequest: suspend (UUID, MultiPartData) -> Unit
) = user {
    authenticate(AuthConstants.USER_AUTH) {
        get<Profile> {
            val userId = call.principalOrThrow<UserPrincipal>().userId

            val userProfile = getUserProfileRequest.invoke(userId)
            call.respondSuccess(
                UserResponse.fromDomain(
                    data = userProfile,
                    baseUrl = application.getBaseUrl(),
                    uploadFolder = SystemEnvVariablesUtil.uploadFolder,
                    folderUrl = SystemEnvVariablesUtil.avatarFolder
                )
            )
        }

        patch<Profile> {
            val userId = call.principalOrThrow<UserPrincipal>().userId
            val userData = call.receive<UserProfileBody>()

            val userProfile = patchUserProfileRequest.invoke(userId, userData)
            call.respondSuccess(
                UserResponse.fromDomain(
                    data = userProfile,
                    baseUrl = application.getBaseUrl(),
                    uploadFolder = SystemEnvVariablesUtil.uploadFolder,
                    folderUrl = SystemEnvVariablesUtil.avatarFolder
                )
            )
        }

        post<PostAvatar> {
            val userId = call.principalOrThrow<UserPrincipal>().userId

            val avatar = call.receiveMultipart()

            postUserAvatarRequest.invoke(userId, avatar)
            call.respondSuccessCreated()
        }
    }
}

private fun Route.userAdmin(
    deleteUserProfileRequest: suspend (UUID) -> Unit
) = admin {
    authenticate(AuthConstants.ADMIN_AUTH) {
        delete<DeleteProfile> { param ->
            verifyRoles(setOf(UserRole.ADMIN))

            deleteUserProfileRequest.invoke(param.userId)
            call.respondSuccessNoContent()
        }
    }
}
