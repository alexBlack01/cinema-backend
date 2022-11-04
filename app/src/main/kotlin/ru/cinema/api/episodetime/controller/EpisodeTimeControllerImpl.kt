package ru.cinema.api.episodetime.controller

import ru.cinema.api.episodetime.model.EpisodeTimeBody
import ru.cinema.domain.episodetime.GetEpisodeTimeByUserUseCase
import ru.cinema.domain.episodetime.PostEpisodeTimeByUserUseCase
import java.util.*

class EpisodeTimeControllerImpl(
    private val getEpisodeTimeByUserUseCase: GetEpisodeTimeByUserUseCase,
    private val postEpisodeTimeByUserUseCase: PostEpisodeTimeByUserUseCase
) : EpisodeTimeController {

    override suspend fun getEpisodeTimeByUser(userId: UUID, episodeId: UUID): Int? {
        return getEpisodeTimeByUserUseCase(userId to episodeId).getOrThrow()
    }

    override suspend fun postEpisodeTimeByUser(userId: UUID, episodeId: UUID, episodeTime: EpisodeTimeBody) {
        postEpisodeTimeByUserUseCase(episodeTime.toDomain(userId, episodeId)).getOrThrow()
    }
}
