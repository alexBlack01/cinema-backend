package ru.cinema.domain.episodecomment

import ru.cinema.domain.common.usecase.UseCase
import ru.cinema.domain.episodecomment.model.Comment
import ru.cinema.domain.episodecomment.model.CommentWithoutUserData
import ru.cinema.domain.user.UserDbDataSource
import java.util.UUID

interface GetCommentsByEpisodeUseCase : UseCase<UUID, List<Comment>>

class GetCommentsByEpisodeUseCaseImpl(
    private val commentDbDataSource: CommentDbDataSource,
    private val userDbDataSource: UserDbDataSource
) : GetCommentsByEpisodeUseCase {

    /**
     * @param param is episode id
     */
    override suspend fun execute(param: UUID): Result<List<Comment>> {
        val comments = commentDbDataSource.getCommentsByEpisode(param)

        val commentsWithUserData = addUserDataInComment(comments)

        return successResult(commentsWithUserData)
    }

    private suspend fun addUserDataInComment(comments: List<CommentWithoutUserData>): List<Comment> {
        val commentsWithUserData: MutableList<Comment> = mutableListOf()

        comments.forEach { comment ->
            val author = userDbDataSource.getUserById(comment.authorId)

            commentsWithUserData.add(
                Comment(
                    commentId = comment.commentId,
                    creationDateTime = comment.creationDateTime,
                    authorName = "${author?.lastName} ${author?.lastName}",
                    authorAvatar = author?.avatar.orEmpty(),
                    text = comment.text
                )
            )
        }

        return commentsWithUserData
    }
}
