package ru.cinema.data.db.episode

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import ru.cinema.data.db.common.extensions.DatabaseDataSource
import ru.cinema.data.db.episode.model.EpisodeEntity
import ru.cinema.data.db.episode.model.EpisodeTable
import ru.cinema.data.db.episodestar.model.EpisodeStarTable
import ru.cinema.data.db.image.model.EpisodeImageEntity
import ru.cinema.data.db.movie.model.MovieEntity
import ru.cinema.data.db.star.model.StarEntity
import ru.cinema.domain.episode.EpisodeDbDataSource
import ru.cinema.domain.episode.model.EditEpisodeForm
import ru.cinema.domain.episode.model.Episode
import ru.cinema.domain.episode.model.EpisodeForm
import ru.cinema.domain.episode.model.ShortEpisodeInfo
import java.util.*

class EpisodeDbDataSourceImpl(
    override val db: Database
) : EpisodeDbDataSource, DatabaseDataSource {

    override suspend fun getEpisodesById(episodeIds: List<UUID>): List<ShortEpisodeInfo> = dbQuery {
        EpisodeEntity.find { EpisodeTable.id inList episodeIds }.map { it.toDomainShortEpisodeInfo() }
    }

    override suspend fun getEpisodeById(episodeId: UUID): ShortEpisodeInfo? = dbQuery {
        EpisodeEntity.findById(episodeId)?.toDomainShortEpisodeInfo()
    }

    override suspend fun insertNewEpisode(episodeData: EpisodeForm): Episode = dbQuery {
        val episode = EpisodeEntity.new {
            this.name = episodeData.name
            this.description = episodeData.description
            this.movieId = MovieEntity[movieId].id
            this.director = episodeData.director
            this.year = episodeData.year.toString()
            this.runtime = episodeData.runtime.toString()
        }

        episodeData.stars.forEach { starId ->
            if (StarEntity.findById(starId)?.id != null) {
                EpisodeStarTable.insert {
                    it[this.episodeId] = episode.id.value
                    it[this.starId] = starId
                }
            }
        }

        episode.toDomain()
    }

    override suspend fun insertEpisodeFiles(
        episodeId: UUID,
        images: List<String>,
        preview: String?,
        filePath: String?
    ) {
        val episode = EpisodeEntity.findById(episodeId)

        episode?.preview = preview
        episode?.filePath = filePath

        images.forEach { imageName ->
            EpisodeImageEntity.new {
                this.url = imageName
                this.episodeId = EpisodeEntity[episodeId].id
            }
        }
    }

    override suspend fun editEpisode(editEpisodeForm: EditEpisodeForm): Episode? = dbQuery {
        val episode = EpisodeEntity.findById(editEpisodeForm.episodeId)?.apply {
            editEpisodeForm.name?.let { name ->
                this.name = name
            }
            editEpisodeForm.description?.let { description ->
                this.description = description
            }
            editEpisodeForm.director?.let { director ->
                this.director = director
            }
            editEpisodeForm.year?.let { year ->
                this.year = year.toString()
            }
            editEpisodeForm.runtime?.let { runtime ->
                this.runtime = runtime.toString()
            }
        }

        editEpisodeForm.stars?.let { stars ->
            EpisodeStarTable.deleteWhere {
                EpisodeStarTable.episodeId eq editEpisodeForm.episodeId and (EpisodeStarTable.starId notInList stars)
            }

            stars.forEach { starId ->
                if (!EpisodeStarTable.select(EpisodeStarTable.starId eq starId).any()) {
                    EpisodeStarTable.insert {
                        it[this.episodeId] = editEpisodeForm.episodeId
                        it[this.starId] = starId
                    }
                }
            }
        }

        episode?.toDomain()
    }

    override suspend fun deleteEpisodeById(movieId: UUID, episodeId: UUID) = dbQueryWithoutResult {
        EpisodeTable.deleteWhere { EpisodeTable.movieId eq movieId and (EpisodeTable.id eq episodeId) }
    }
}
