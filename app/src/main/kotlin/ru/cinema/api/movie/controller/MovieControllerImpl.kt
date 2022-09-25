package ru.cinema.api.movie.controller

import ru.cinema.domain.episode.GetEpisodesByMovieUseCase
import ru.cinema.domain.episode.model.Episode
import ru.cinema.domain.movie.GetAllMoviesUseCase
import ru.cinema.domain.movie.model.Movie
import ru.cinema.domain.movie.model.MovieType
import java.util.*

class MovieControllerImpl(
    private val getAllMovieUseCase: GetAllMoviesUseCase,
    private val getEpisodesByMovieUseCase: GetEpisodesByMovieUseCase
) : MovieController {

    override suspend fun getAllMovies(movieType: MovieType): List<Movie> {
        return getAllMovieUseCase(movieType).getOrThrow()
    }

    override suspend fun getEpisodesByMovie(movieId: UUID): List<Episode> {
        return getEpisodesByMovieUseCase(movieId).getOrThrow()
    }
}
