package ru.cinema.domain.movie

import ru.cinema.domain.common.error.MovieNotFound
import ru.cinema.domain.common.usecase.UseCase
import ru.cinema.domain.movie.model.EditMovieForm
import ru.cinema.domain.movie.model.Movie

interface PatchMovieUseCase : UseCase<EditMovieForm, Movie>
class PatchMovieUseCaseImpl(
    private val movieDbDataSource: MovieDbDataSource
) : PatchMovieUseCase {

    override suspend fun execute(param: EditMovieForm): Result<Movie> {
        movieDbDataSource.getMovieById(param.movieId) ?: throw MovieNotFound()

        val newMovie = movieDbDataSource.editMovie(param) ?: throw MovieNotFound()
        return successResult(newMovie)
    }
}
