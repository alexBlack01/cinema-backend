package ru.cinema.data.db.dislike

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import ru.cinema.data.db.common.extensions.DatabaseDataSource
import ru.cinema.data.db.dislike.model.DislikeTable
import ru.cinema.data.db.user.model.UserEntity
import ru.cinema.domain.dislike.DislikeDbDataSource
import java.util.*

class DislikeDbDataSourceImpl(
    override val db: Database
) : DislikeDbDataSource, DatabaseDataSource {

    override suspend fun getUserDislikes(userId: UUID): List<UUID> = dbQuery {
        UserEntity.findById(userId)?.dislikes
            ?.map { it.id.value } ?: emptyList()
    }

    override suspend fun postDislikeByUser(userId: UUID, movieId: UUID) = dbQueryWithoutResult {
        DislikeTable.insert {
            it[this.userId] = userId
            it[this.movieId] = movieId
        }
    }
}
