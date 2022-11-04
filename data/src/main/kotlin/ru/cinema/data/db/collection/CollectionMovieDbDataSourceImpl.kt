package ru.cinema.data.db.collection

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import ru.cinema.data.db.collection.model.CollectionEntity
import ru.cinema.data.db.collectionmovie.model.CollectionMovieTable
import ru.cinema.data.db.common.extensions.DatabaseDataSource
import ru.cinema.data.db.movie.model.MovieTable
import ru.cinema.domain.collectionmovie.CollectionMovieDbDataSource
import ru.cinema.domain.movie.model.Movie
import java.util.*

class CollectionMovieDbDataSourceImpl(
    override val db: Database
) : CollectionMovieDbDataSource, DatabaseDataSource {

    override suspend fun getMoviesByCollection(collectionId: UUID): List<Movie> = dbQuery {
        CollectionEntity.findById(collectionId)?.movies
            ?.orderBy(MovieTable.name to SortOrder.ASC)
            ?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun postMovieToCollection(collectionId: UUID, movieId: UUID) = dbQueryWithoutResult {
        CollectionMovieTable.insert {
            it[this.movieId] = movieId
            it[this.collectionId] = collectionId
        }
    }

    override suspend fun deleteMovieFromCollection(collectionId: UUID, movieId: UUID) = dbQueryWithoutResult {
        CollectionMovieTable.deleteWhere {
            CollectionMovieTable.collectionId eq collectionId and (CollectionMovieTable.movieId eq movieId)
        }
    }
}
