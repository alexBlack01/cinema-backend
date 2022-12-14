package ru.cinema.domain.episodecomment

import ru.cinema.domain.common.error.InvalidUserCredentials
import ru.cinema.domain.common.usecase.UseCase
import ru.cinema.domain.episodecomment.model.Comment
import ru.cinema.domain.episodecomment.model.CommentForm
import ru.cinema.domain.user.UserDbDataSource

interface PostCommentByEpisodeUseCase : UseCase<CommentForm, Comment>
class PostCommentByEpisodeUseCaseImpl(
    private val userDbDataSource: UserDbDataSource,
    private val commentDbDataSource: CommentDbDataSource
) : PostCommentByEpisodeUseCase {
    override suspend fun execute(param: CommentForm): Result<Comment> {
        val user = userDbDataSource.getUserById(param.userId) ?: throw InvalidUserCredentials()

        val comment = commentDbDataSource.postCommentByEpisode(param)

        return successResult(
            Comment(
                commentId = comment.commentId,
                creationDateTime = comment.creationDateTime,
                authorName = "${user.lastName} ${user.lastName}",
                authorAvatar = user.avatar.orEmpty(),
                text = comment.text
            )
        )
    }
}
