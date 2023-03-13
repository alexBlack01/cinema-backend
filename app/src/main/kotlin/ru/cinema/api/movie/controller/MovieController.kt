package ru.cinema.api.movie.controller

import io.ktor.http.content.MultiPartData
import ru.cinema.api.movie.model.MovieBody
import ru.cinema.api.movie.model.MovieEditBody
import ru.cinema.domain.movie.model.Movie
import ru.cinema.domain.movie.model.MovieType
import java.util.UUID

interface MovieController {
    suspend fun getAllMovies(): List<Movie>
    suspend fun getMoviesByFilter(userId: UUID, movieType: MovieType): List<Movie>
    suspend fun postNewMovie(movieData: MovieBody): Movie
    suspend fun insertImagesForMovie(movieId: UUID, images: MultiPartData)
    suspend fun patchMovieById(movieId: UUID, editMovieBody: MovieEditBody): Movie
    suspend fun deleteMovieById(movieId: UUID)
    suspend fun postDislikeByUser(userId: UUID, movieId: UUID)
}
