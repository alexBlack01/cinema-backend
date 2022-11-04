package ru.cinema.data.db.episodecomment

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import ru.cinema.data.db.common.extensions.DatabaseDataSource
import ru.cinema.data.db.episode.model.EpisodeEntity
import ru.cinema.data.db.episodecomment.model.CommentEntity
import ru.cinema.data.db.episodecomment.model.CommentTable
import ru.cinema.data.db.user.model.UserEntity
import ru.cinema.domain.episodecomment.CommentDbDataSource
import ru.cinema.domain.episodecomment.model.CommentForm
import ru.cinema.domain.episodecomment.model.CommentWithoutUserData
import java.util.*

class CommentDbDataSourceImpl(
    override val db: Database
) : CommentDbDataSource, DatabaseDataSource {
    override suspend fun getCommentsByEpisode(episodeId: UUID): List<CommentWithoutUserData> = dbQuery {
        EpisodeEntity.findById(episodeId)?.comments
            ?.orderBy(CommentTable.createdAt to SortOrder.ASC)
            ?.map { it.toDomain() }.orEmpty()
    }

    override suspend fun getCommentById(commentId: UUID): CommentWithoutUserData? = dbQuery {
        CommentEntity.findById(commentId)?.toDomain()
    }

    override suspend fun postCommentByEpisode(commentForm: CommentForm): CommentWithoutUserData = dbQuery {
        val comment = CommentEntity.new {
            this.episodeId = EpisodeEntity[commentForm.episodeId].id
            this.authorId = UserEntity[commentForm.userId].id
            this.text = commentForm.text
        }

        comment.toDomain()
    }

    override suspend fun deleteCommentById(episodeId: UUID, commentId: UUID) = dbQueryWithoutResult {
        CommentTable.deleteWhere { CommentTable.id eq commentId and (CommentTable.episodeId eq episodeId) }
    }
}
