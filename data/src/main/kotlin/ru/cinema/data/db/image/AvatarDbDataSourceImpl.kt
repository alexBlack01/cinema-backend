package ru.cinema.data.db.image

import org.jetbrains.exposed.sql.Database
import ru.cinema.data.db.common.extensions.DatabaseDataSource
import ru.cinema.data.db.image.model.AvatarEntity
import ru.cinema.domain.image.AvatarDbDataSource
import ru.cinema.domain.image.model.Image

class AvatarDbDataSourceImpl(
    override val db: Database
) : AvatarDbDataSource, DatabaseDataSource {

    override suspend fun addAvatar(avatarId: String): Image = dbQuery {
        val avatarForm = AvatarEntity.new {
            this.url = avatarId
        }

        avatarForm.toDomain()
    }
}
