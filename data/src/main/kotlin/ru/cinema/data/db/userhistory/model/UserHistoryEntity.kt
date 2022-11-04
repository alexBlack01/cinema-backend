package ru.cinema.data.db.userhistory.model

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import ru.cinema.data.db.episode.model.EpisodeEntity
import ru.cinema.data.db.episode.model.EpisodeTable
import ru.cinema.data.db.user.model.UserEntity
import ru.cinema.data.db.user.model.UserTable
import ru.cinema.domain.episodetime.model.EpisodeHistoryForm
import java.util.*

object UserHistoryTable : UUIDTable(name = "user_histories") {
    val time = varchar(name = "time", length = 100)
    val userId = reference(name = "user_id", foreign = UserTable, onDelete = ReferenceOption.CASCADE)
    val episodeId = reference(name = "episode_id", foreign = EpisodeTable, onDelete = ReferenceOption.CASCADE)
}

class UserHistoryEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    var time by UserHistoryTable.time
    var userId by UserHistoryTable.userId
    var episodeId by UserHistoryTable.episodeId

    val episode by EpisodeEntity referencedOn UserHistoryTable.episodeId
    val user by UserEntity referencedOn UserHistoryTable.userId

    fun toDomain() = time.toInt()

    fun toDomainHistory() = EpisodeHistoryForm(
        episodeId = episodeId.value,
        time = time.toInt()
    )

    companion object : UUIDEntityClass<UserHistoryEntity>(UserHistoryTable)
}
