package ru.cinema.domain.episodecomment

import ru.cinema.domain.common.error.CommentNotFound
import ru.cinema.domain.common.usecase.UseCase
import java.util.UUID

interface DeleteCommentByAdminUseCase : UseCase<Pair<UUID, UUID>, Unit>

class DeleteCommentByAdminUseCaseImpl(
    private val commentDbDataSource: CommentDbDataSource
) : DeleteCommentByAdminUseCase {

    /**
     * @param param.first is episode id
     * @param param.second is comment id
     */
    override suspend fun execute(param: Pair<UUID, UUID>): Result<Unit> {
        commentDbDataSource.getCommentById(param.second) ?: throw CommentNotFound()

        return successResult(commentDbDataSource.deleteCommentById(param.first, param.second))
    }
}
