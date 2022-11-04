package ru.cinema.domain.episode

import ru.cinema.domain.common.usecase.UseCase
import ru.cinema.domain.episode.model.Episode
import ru.cinema.domain.episode.model.EpisodeForm

interface PostEpisodeByMovieUseCase : UseCase<EpisodeForm, Episode>

class PostEpisodeByMovieUseCaseImpl(
    private val episodeDbDataSource: EpisodeDbDataSource
) : PostEpisodeByMovieUseCase {

    override suspend fun execute(param: EpisodeForm): Result<Episode> {
        return successResult(episodeDbDataSource.insertNewEpisode(param))
    }
}
