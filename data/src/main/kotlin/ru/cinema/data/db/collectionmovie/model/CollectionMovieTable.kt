package ru.cinema.data.db.collectionmovie.model

import org.jetbrains.exposed.sql.Table
import ru.cinema.data.db.collection.model.CollectionTable
import ru.cinema.data.db.movie.model.MovieTable

object CollectionMovieTable : Table(name = "collections_movies") {
    val collectionId = reference(name = "collection_id", foreign = CollectionTable)
    val movieId = reference(name = "movie_id", foreign = MovieTable)
    override val primaryKey = PrimaryKey(collectionId, movieId, name = "PK_CollectionMovie_collection_id_movie_id")
}
