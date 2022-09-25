package ru.cinema.data.db.episodestar.model

import org.jetbrains.exposed.sql.Table
import ru.cinema.data.db.episode.model.EpisodeTable
import ru.cinema.data.db.star.model.StarTable

object EpisodeStarTable : Table(name = "episodes_stars") {
    val episodeId = reference(name = "episode_id", foreign = EpisodeTable)
    val starId = reference(name = "star_id", foreign = StarTable)
    override val primaryKey = PrimaryKey(episodeId, starId, name = "PK_EpisodeStar_episode_id_star_id")
}
