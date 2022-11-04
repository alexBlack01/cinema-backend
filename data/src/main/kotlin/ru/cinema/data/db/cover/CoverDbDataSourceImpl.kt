package ru.cinema.data.db.cover

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.deleteAll
import ru.cinema.data.db.common.extensions.DatabaseDataSource
import ru.cinema.data.db.cover.model.CoverEntity
import ru.cinema.data.db.cover.model.CoverTable
import ru.cinema.data.db.movie.model.MovieEntity
import ru.cinema.domain.cover.CoverDbDataSource
import ru.cinema.domain.cover.model.Cover

class CoverDbDataSourceImpl(
    override val db: Database
) : CoverDbDataSource, DatabaseDataSource {

    override suspend fun getCover(): Cover? = dbQuery {
        CoverEntity.all().firstOrNull()?.toDomain()
    }

    override suspend fun updateCover(coverData: Cover) = dbQueryWithoutResult {
        CoverTable.deleteAll()

        CoverEntity.new {
            this.movieId = MovieEntity[coverData.movieId].id
            this.backgroundImage = coverData.backgroundImage
            this.foregroundImage = coverData.foregroundImage
        }
    }
}
