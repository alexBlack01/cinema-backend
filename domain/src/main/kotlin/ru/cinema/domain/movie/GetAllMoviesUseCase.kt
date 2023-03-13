package ru.cinema.domain.movie

import ru.cinema.domain.common.usecase.UseCaseWithoutParam
import ru.cinema.domain.movie.model.Movie

interface GetAllMoviesUseCase : UseCaseWithoutParam<List<Movie>>

class GetAllMoviesUseCaseImpl(
    private val movieDbDataSource: MovieDbDataSource
) : GetAllMoviesUseCase {
    override suspend fun execute(): Result<List<Movie>> {
        return successResult(movieDbDataSource.getAllMovies())
    }
}
