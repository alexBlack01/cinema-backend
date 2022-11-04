package ru.cinema.domain.episodetime

import ru.cinema.domain.episodetime.model.EpisodeHistoryForm
import ru.cinema.domain.episodetime.model.EpisodeTime
import java.util.UUID

interface EpisodeTimeDbDataSource {

    suspend fun getEpisodeTimeByUser(userId: UUID, episodeId: UUID): Int?

    suspend fun postEpisodeTimeByUser(episodeTime: EpisodeTime)

    suspend fun getHistoryByUserUseCase(userId: UUID): List<EpisodeHistoryForm>
}
