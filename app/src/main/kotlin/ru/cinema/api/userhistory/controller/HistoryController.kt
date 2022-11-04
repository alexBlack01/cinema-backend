package ru.cinema.api.userhistory.controller

import ru.cinema.domain.episodetime.model.EpisodeWithTime
import java.util.*

interface HistoryController {

    suspend fun getHistoryByUser(userId: UUID): List<EpisodeWithTime>
}
