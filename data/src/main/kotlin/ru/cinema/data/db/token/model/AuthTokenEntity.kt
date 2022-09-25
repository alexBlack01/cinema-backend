package ru.cinema.data.db.token.model

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import ru.cinema.data.db.user.model.UserEntity
import ru.cinema.data.db.user.model.UserTable
import ru.cinema.domain.auth.token.model.AuthTokenInfo
import java.util.*

object AuthTokenTable : UUIDTable(name = "auth_tokens") {
    val refreshToken = varchar(name = "refresh_token", length = 256)
    val refreshTokenExpiresIn = long(name = "refresh_token_expires_in")
    val userId = reference(name = "user_id", foreign = UserTable, onDelete = ReferenceOption.CASCADE)
}

class AuthTokenEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    var refreshToken by AuthTokenTable.refreshToken
    var refreshTokenExpiresIn by AuthTokenTable.refreshTokenExpiresIn
    var userId by AuthTokenTable.userId

    val user by UserEntity referencedOn AuthTokenTable.userId

    fun toDomain() = AuthTokenInfo(
        token = refreshToken,
        tokenExpiresIn = refreshTokenExpiresIn
    )

    companion object : UUIDEntityClass<AuthTokenEntity>(AuthTokenTable)
}
