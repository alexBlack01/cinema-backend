package ru.cinema.api.movie.controller

import ru.cinema.domain.episode.model.Episode
import ru.cinema.domain.movie.model.Movie
import ru.cinema.domain.movie.model.MovieType
import java.util.UUID

interface MovieController {
    suspend fun getAllMovies(movieType: MovieType): List<Movie>
    suspend fun getEpisodesByMovie(movieId: UUID): List<Episode>
}
