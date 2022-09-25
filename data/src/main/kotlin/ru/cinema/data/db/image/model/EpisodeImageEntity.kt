package ru.cinema.data.db.image.model

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import ru.cinema.data.db.episode.model.EpisodeEntity
import ru.cinema.data.db.episode.model.EpisodeTable
import java.util.*

object EpisodeImageTable : UUIDTable(name = "episode_images") {
    val url = varchar(name = "url", length = 150)
    val episodeId = reference(name = "episode_id", foreign = EpisodeTable, onDelete = ReferenceOption.CASCADE)
}

class EpisodeImageEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    var url by EpisodeImageTable.url

    var episode by EpisodeEntity referencedOn EpisodeImageTable.episodeId

    companion object : UUIDEntityClass<EpisodeImageEntity>(EpisodeImageTable)
}
