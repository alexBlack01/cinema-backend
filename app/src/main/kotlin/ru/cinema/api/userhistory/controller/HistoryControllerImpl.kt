package ru.cinema.api.userhistory.controller

import ru.cinema.domain.episodetime.GetHistoryByUserUseCase
import ru.cinema.domain.episodetime.model.EpisodeWithTime
import java.util.*

class HistoryControllerImpl(
    private val getHistoryByUserUseCase: GetHistoryByUserUseCase
) : HistoryController {

    override suspend fun getHistoryByUser(userId: UUID): List<EpisodeWithTime> {
        return getHistoryByUserUseCase(userId).getOrThrow()
    }
}
