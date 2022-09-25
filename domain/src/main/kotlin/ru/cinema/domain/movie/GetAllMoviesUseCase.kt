package ru.cinema.domain.movie

import ru.cinema.domain.common.usecase.UseCase
import ru.cinema.domain.movie.model.Movie
import ru.cinema.domain.movie.model.MovieType

interface GetAllMoviesUseCase : UseCase<MovieType, List<Movie>>

class GetAllMoviesUseCaseImpl(
    private val movieDataSource: MovieDbDataSource
) : GetAllMoviesUseCase {

    override suspend fun execute(param: MovieType): Result<List<Movie>> {
        return when (param) {
            MovieType.NEW -> successResult(movieDataSource.getNewMovies())
            MovieType.FOR_ME -> successResult(movieDataSource.getPersonalMovies())
            MovieType.COMPILATION -> successResult(movieDataSource.getCompilationMovies())
            MovieType.IN_TREND -> successResult(movieDataSource.getTrendMovies())
            MovieType.LAST_VIEW -> successResult(movieDataSource.getLastViewMovies())
        }
    }
}
