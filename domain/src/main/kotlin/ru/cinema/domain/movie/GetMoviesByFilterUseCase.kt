package ru.cinema.domain.movie

import ru.cinema.domain.common.usecase.UseCase
import ru.cinema.domain.movie.model.Movie
import ru.cinema.domain.movie.model.MovieType
import java.util.UUID

interface GetMoviesByFilterUseCase : UseCase<Pair<UUID, MovieType>, List<Movie>>

class GetMoviesByFilterUseCaseImpl(
    private val movieDataSource: MovieDbDataSource
) : GetMoviesByFilterUseCase {
    /**
     * @param param.first is user id
     * @param param.second is movie type
     */
    override suspend fun execute(param: Pair<UUID, MovieType>): Result<List<Movie>> {
        return when (param.second) {
            MovieType.NEW -> successResult(movieDataSource.getNewMovies(param.first))
            MovieType.FOR_ME -> successResult(movieDataSource.getPersonalMovies(param.first))
            MovieType.COMPILATION -> successResult(movieDataSource.getCompilationMovies(param.first))
            MovieType.IN_TREND -> successResult(movieDataSource.getTrendMovies(param.first))
            MovieType.LAST_VIEW -> successResult(movieDataSource.getLastViewMovies(param.first))
        }
    }
}
