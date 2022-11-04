package ru.cinema.domain.movie

import ru.cinema.domain.cover.model.Cover
import ru.cinema.domain.episode.model.Episode
import ru.cinema.domain.movie.model.EditMovieForm
import ru.cinema.domain.movie.model.Movie
import ru.cinema.domain.movie.model.MovieForm
import ru.cinema.domain.movie.model.ShortMovieInfo
import java.util.*

interface MovieDbDataSource {
    suspend fun getNewMovies(userId: UUID): List<Movie>
    suspend fun getTrendMovies(userId: UUID): List<Movie>
    suspend fun getPersonalMovies(userId: UUID): List<Movie>
    suspend fun getLastViewMovies(userId: UUID): List<Movie>
    suspend fun getCompilationMovies(userId: UUID): List<Movie>
    suspend fun getAllMovies(): List<Movie>
    suspend fun getEpisodesByMovie(movieId: UUID): List<Episode>
    suspend fun getMoviesById(movieIds: List<UUID>): List<ShortMovieInfo>
    suspend fun getMovieById(movieId: UUID): Movie?
    suspend fun getBestMovieByViewCount(): Cover?
    suspend fun insertNewMovie(movieData: MovieForm): Movie
    suspend fun insertMovieImages(
        movieId: UUID,
        posterName: String?,
        images: List<String>,
        backgroundImage: String?,
        foregroundImage: String?
    )
    suspend fun editMovie(editMovieForm: EditMovieForm): Movie?
    suspend fun deleteMovieById(movieId: UUID)
}
