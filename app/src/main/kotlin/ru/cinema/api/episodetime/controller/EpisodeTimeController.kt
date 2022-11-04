package ru.cinema.api.episodetime.controller

import ru.cinema.api.episodetime.model.EpisodeTimeBody
import java.util.*

interface EpisodeTimeController {

    suspend fun getEpisodeTimeByUser(userId: UUID, episodeId: UUID): Int?

    suspend fun postEpisodeTimeByUser(userId: UUID, episodeId: UUID, episodeTime: EpisodeTimeBody)
}
