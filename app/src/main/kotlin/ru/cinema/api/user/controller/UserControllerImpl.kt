package ru.cinema.api.user.controller

import io.ktor.http.content.MultiPartData
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import ru.cinema.api.common.utils.ContentUtils
import ru.cinema.api.common.utils.FileUrlUtils
import ru.cinema.api.common.validation.isValidateImage
import ru.cinema.api.user.model.UserProfileBody
import ru.cinema.domain.movie.model.ContentForm
import ru.cinema.domain.user.DeleteUserByAdminUseCase
import ru.cinema.domain.user.GetUserUseCase
import ru.cinema.domain.user.PatchUserUseCase
import ru.cinema.domain.user.PostAvatarByUserIdUseCase
import ru.cinema.domain.user.model.AvatarParams
import ru.cinema.domain.user.model.UserProfile
import java.util.*

class UserControllerImpl(
    private val getUserUseCase: GetUserUseCase,
    private val patchUserUseCase: PatchUserUseCase,
    private val postAvatarByUserIdUseCase: PostAvatarByUserIdUseCase,
    private val deleteUserByAdminUseCase: DeleteUserByAdminUseCase
) : UserController {
    override suspend fun getUserProfile(userId: UUID): UserProfile {
        return getUserUseCase(userId).getOrThrow()
    }

    override suspend fun patchUserProfile(userId: UUID, userProfileBody: UserProfileBody): UserProfile {
        return patchUserUseCase(userProfileBody.toDomain(userId)).getOrThrow()
    }

    override suspend fun postAvatarByUserId(userId: UUID, avatar: MultiPartData) {
        var avatarBytes: ContentForm? = null

        avatar.forEachPart { part ->
            if (part is PartData.FileItem) {
                avatarBytes = ContentUtils.createContentForm(
                    part,
                    FileUrlUtils.getUrlForAvatarContent()
                ).apply { isValidateImage() }
            }
        }

        postAvatarByUserIdUseCase(
            AvatarParams(
                userId = userId,
                avatar = avatarBytes
            )
        ).getOrThrow()
    }

    override suspend fun deleteUserByAdmin(userId: UUID) {
        deleteUserByAdminUseCase(userId)
    }
}
