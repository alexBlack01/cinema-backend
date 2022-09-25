package ru.cinema.data.db.star.model

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import ru.cinema.data.db.episode.model.EpisodeEntity
import ru.cinema.data.db.episodestar.model.EpisodeStarTable
import java.util.*

object StarTable : UUIDTable(name = "stars") {
    val name = varchar(name = "name", length = 150)
}

class StarEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    var name by StarTable.name

    val episodes by EpisodeEntity via EpisodeStarTable

    companion object : UUIDEntityClass<StarEntity>(StarTable)
}
