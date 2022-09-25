package ru.cinema.data.db.user.model

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import ru.cinema.data.db.image.model.AvatarEntity
import ru.cinema.data.db.image.model.AvatarTable
import ru.cinema.data.db.userrole.model.UserRoleEntity
import ru.cinema.data.db.userrole.model.UserRoleTable
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
