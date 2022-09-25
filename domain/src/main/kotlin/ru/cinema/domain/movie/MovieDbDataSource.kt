package ru.cinema.domain.movie

import ru.cinema.domain.episode.model.Episode
import ru.cinema.domain.movie.model.Movie
import java.util.*

interface MovieDbDataSource {
    suspend fun getNewMovies(): List<Movie>
    suspend fun getTrendMovies(): List<Movie>
    suspend fun getPersonalMovies(): List<Movie>
    suspend fun getLastViewMovies(): List<Movie>
    suspend fun getCompilationMovies(): List<Movie>
    suspend fun getAllMovies(): List<Movie>
    suspend fun getEpisodesByMovie(movieId: UUID): List<Episode>
}
