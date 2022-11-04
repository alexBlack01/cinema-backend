package ru.cinema.domain.episode

import ru.cinema.domain.common.error.EpisodeNotFound
import ru.cinema.domain.common.usecase.UseCase
import ru.cinema.domain.episode.model.EditEpisodeForm
import ru.cinema.domain.episode.model.Episode

interface PatchEpisodeUseCase : UseCase<EditEpisodeForm, Episode>

class PatchEpisodeUseCaseImpl(
    private val episodeDbDataSource: EpisodeDbDataSource
) : PatchEpisodeUseCase {

    override suspend fun execute(param: EditEpisodeForm): Result<Episode> {
        episodeDbDataSource.getEpisodeById(param.episodeId) ?: throw EpisodeNotFound()

        val newEpisode = episodeDbDataSource.editEpisode(param) ?: throw EpisodeNotFound()
        return successResult(newEpisode)
    }
}
