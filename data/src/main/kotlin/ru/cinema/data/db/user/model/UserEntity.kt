package ru.cinema.data.db.user.model

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import ru.cinema.data.db.chat.model.ChatEntity
import ru.cinema.data.db.collection.model.CollectionEntity
import ru.cinema.data.db.collection.model.CollectionTable
import ru.cinema.data.db.dislike.model.DislikeTable
import ru.cinema.data.db.episodecomment.model.CommentEntity
import ru.cinema.data.db.episodecomment.model.CommentTable
import ru.cinema.data.db.image.model.AvatarEntity
import ru.cinema.data.db.image.model.AvatarTable
import ru.cinema.data.db.movie.model.MovieEntity
import ru.cinema.data.db.tag.model.TagEntity
import ru.cinema.data.db.userchat.model.UserChatTable
import ru.cinema.data.db.userhistory.model.UserHistoryEntity
import ru.cinema.data.db.userhistory.model.UserHistoryTable
import ru.cinema.data.db.userrole.model.UserRoleEntity
import ru.cinema.data.db.userrole.model.UserRoleTable
import ru.cinema.data.db.usertag.model.UserTagTable
import ru.cinema.domain.user.model.User
import ru.cinema.domain.user.model.UserProfile
import java.util.*

object UserTable : UUIDTable(name = "users") {
    val email = varchar(name = "email", length = 255)
    val password = varchar(name = "password", length = 255)
    val firstName = varchar(name = "first_name", length = 255)
    val lastName = varchar(name = "last_name", length = 255)
    val avatarId = optReference(name = "avatar_id", foreign = AvatarTable)
}

class UserEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    var email by UserTable.email
    var password by UserTable.password
    var firstName by UserTable.firstName
    var lastName by UserTable.lastName

    val avatar by AvatarEntity optionalReferencedOn UserTable.avatarId
    val roles by UserRoleEntity referrersOn UserRoleTable.userId
    val tags by TagEntity via UserTagTable
    val chats by ChatEntity via UserChatTable
    val histories by UserHistoryEntity referrersOn UserHistoryTable.userId
    val collections by CollectionEntity referrersOn CollectionTable.userId
    val dislikes by MovieEntity via DislikeTable
    val comments by CommentEntity referrersOn CommentTable.authorId

    fun toDomain() = User(
        id = id.value,
        email = email,
        password = password
    )

    fun toDomainWithoutPassword() = UserProfile(
        id = id.value,
        email = email,
        firstName = firstName,
        lastName = lastName,
        avatar = avatar?.url
    )

    companion object : UUIDEntityClass<UserEntity>(UserTable)
}
