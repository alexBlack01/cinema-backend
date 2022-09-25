package ru.cinema.data.db.movie

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SortOrder
import ru.cinema.data.db.common.extensions.DatabaseDataSource
import ru.cinema.data.db.episode.model.EpisodeTable
import ru.cinema.data.db.movie.model.MovieEntity
import ru.cinema.data.db.movie.model.MovieTable
import ru.cinema.domain.episode.model.Episode
import ru.cinema.domain.movie.MovieDbDataSource
import ru.cinema.domain.movie.model.Movie
import java.util.*

class MovieDbDataSourceImpl(
    override val db: Database
) : MovieDbDataSource, DatabaseDataSource {

    @Suppress("NotImplementedDeclaration")
    override suspend fun getNewMovies(): List<Movie> {
        @Suppress("StringLiteralDuplication")
        TODO("Not yet implemented")
    }

    @Suppress("NotImplementedDeclaration")
    override suspend fun getCompilationMovies(): List<Movie> {
        TODO("Not yet implemented")
    }

    @Suppress("NotImplementedDeclaration")
    override suspend fun getPersonalMovies(): List<Movie> {
        TODO("Not yet implemented")
    }

    @Suppress("NotImplementedDeclaration")
    override suspend fun getLastViewMovies(): List<Movie> {
        TODO("Not yet implemented")
    }

    @Suppress("NotImplementedDeclaration")
    override suspend fun getTrendMovies(): List<Movie> {
        TODO("Not yet implemented")
    }

    @Suppress("NotImplementedDeclaration")
    override suspend fun getAllMovies(): List<Movie> = dbQuery {
        getAll()
    }

    override suspend fun getEpisodesByMovie(movieId: UUID): List<Episode> = dbQuery {
        MovieEntity.findById(movieId)?.episodes
            ?.orderBy(EpisodeTable.name to SortOrder.ASC)
            ?.map { it.toDomain() }.orEmpty()
    }

    private fun getAll(): List<Movie> = MovieEntity
        .all()
        .orderBy(MovieTable.name to SortOrder.ASC)
        .map { it.toDomain() }
}
