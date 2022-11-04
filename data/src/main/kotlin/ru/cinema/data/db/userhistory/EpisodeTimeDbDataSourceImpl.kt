package ru.cinema.data.db.userhistory

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.and
import ru.cinema.data.db.common.extensions.DatabaseDataSource
import ru.cinema.data.db.episode.model.EpisodeEntity
import ru.cinema.data.db.user.model.UserEntity
import ru.cinema.data.db.userhistory.model.UserHistoryEntity
import ru.cinema.data.db.userhistory.model.UserHistoryTable
import ru.cinema.domain.episodetime.EpisodeTimeDbDataSource
import ru.cinema.domain.episodetime.model.EpisodeHistoryForm
import ru.cinema.domain.episodetime.model.EpisodeTime
import java.util.*

class EpisodeTimeDbDataSourceImpl(
    override val db: Database
) : EpisodeTimeDbDataSource, DatabaseDataSource {

    override suspend fun getEpisodeTimeByUser(userId: UUID, episodeId: UUID): Int? = dbQuery {
        UserHistoryEntity.find {
            UserHistoryTable.userId eq userId and (UserHistoryTable.episodeId eq episodeId)
        }.firstOrNull()?.toDomain()
    }

    override suspend fun postEpisodeTimeByUser(episodeTime: EpisodeTime) = dbQueryWithoutResult {
        UserHistoryEntity.new {
            this.userId = UserEntity[episodeTime.userId].id
            this.episodeId = EpisodeEntity[episodeTime.episodeId].id
            this.time = episodeTime.timeInSeconds.toString()
        }
    }

    override suspend fun getHistoryByUserUseCase(userId: UUID): List<EpisodeHistoryForm> = dbQuery {
        UserEntity.findById(userId)?.histories?.map { it.toDomainHistory() } ?: emptyList()
    }
}
