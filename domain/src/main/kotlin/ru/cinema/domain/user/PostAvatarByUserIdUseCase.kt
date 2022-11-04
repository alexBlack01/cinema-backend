package ru.cinema.domain.user

import ru.cinema.domain.common.error.UserNotFound
import ru.cinema.domain.common.usecase.UseCase
import ru.cinema.domain.common.utils.ImageUtils
import ru.cinema.domain.image.AvatarDbDataSource
import ru.cinema.domain.image.ImageLocalDataSource
import ru.cinema.domain.image.model.Image
import ru.cinema.domain.movie.model.ContentForm
import ru.cinema.domain.user.model.AvatarParams

interface PostAvatarByUserIdUseCase : UseCase<AvatarParams, Unit>

class PostAvatarByUserIdUseCaseImpl(
    private val userDbDataSource: UserDbDataSource,
    private val avatarDbDataSource: AvatarDbDataSource,
    private val imageLocalDataSource: ImageLocalDataSource
) : PostAvatarByUserIdUseCase {

    override suspend fun execute(param: AvatarParams): Result<Unit> {
        userDbDataSource.getUserById(param.userId) ?: throw UserNotFound()

        val avatar = param.avatar?.let { saveImage(it) }

        return successResult(userDbDataSource.insertAvatarInProfile(param.userId, avatar?.id))
    }

    private suspend fun saveImage(content: ContentForm): Image {
        val avatarName = ImageUtils.generateFileName(content.fileType)

        imageLocalDataSource.saveImage(content.fileBytes, content.filePath, avatarName)

        return avatarDbDataSource.addAvatar(avatarName)
    }
}
