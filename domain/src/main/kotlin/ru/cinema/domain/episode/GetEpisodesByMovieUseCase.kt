package ru.cinema.domain.episode

import ru.cinema.domain.common.usecase.UseCase
import ru.cinema.domain.episode.model.Episode
import ru.cinema.domain.movie.MovieDbDataSource
import java.util.*

interface GetEpisodesByMovieUseCase : UseCase<UUID, List<Episode>>

class GetEpisodesByMovieUseCaseImpl(
    private val movieDataSource: MovieDbDataSource
) : GetEpisodesByMovieUseCase {
    /**
     * @param param is movie id
     */
    override suspend fun execute(param: UUID): Result<List<Episode>> {
        return successResult(movieDataSource.getEpisodesByMovie(param))
    }
}
