package ru.cinema.domain.collectionmovie

import ru.cinema.domain.movie.model.Movie
import java.util.UUID

interface CollectionMovieDbDataSource {

    suspend fun getMoviesByCollection(collectionId: UUID): List<Movie>

    suspend fun postMovieToCollection(collectionId: UUID, movieId: UUID)

    suspend fun deleteMovieFromCollection(collectionId: UUID, movieId: UUID)
}
