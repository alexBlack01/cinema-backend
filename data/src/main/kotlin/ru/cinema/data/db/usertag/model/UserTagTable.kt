package ru.cinema.data.db.usertag.model

import org.jetbrains.exposed.sql.Table
import ru.cinema.data.db.tag.model.TagTable
import ru.cinema.data.db.user.model.UserTable

object UserTagTable : Table(name = "users_tags") {
    val userId = reference(name = "user_id", foreign = UserTable)
    val tagId = reference(name = "tag_id", foreign = TagTable)
    override val primaryKey = PrimaryKey(userId, tagId, name = "PK_UserTag_user_id_tag_id")
}
