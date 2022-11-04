package ru.cinema.domain.movie

import ru.cinema.domain.common.usecase.UseCase
import ru.cinema.domain.movie.model.Movie
import ru.cinema.domain.movie.model.MovieForm

interface PostMovieUseCase : UseCase<MovieForm, Movie>

class PostMovieUseCaseImpl(
    private val movieDbDataSource: MovieDbDataSource,
) : PostMovieUseCase {

    override suspend fun execute(param: MovieForm): Result<Movie> {
        return successResult(movieDbDataSource.insertNewMovie(param))
    }
}
