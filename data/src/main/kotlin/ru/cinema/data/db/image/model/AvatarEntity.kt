package ru.cinema.data.db.image.model

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object AvatarTable : UUIDTable(name = "avatars") {
    val url = varchar(name = "url", length = 150)
}

class AvatarEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    var url by AvatarTable.url

    companion object : UUIDEntityClass<AvatarEntity>(AvatarTable)
}
