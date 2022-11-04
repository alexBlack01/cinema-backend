package ru.cinema.data.db.token

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import ru.cinema.data.db.common.extensions.DatabaseDataSource
import ru.cinema.data.db.token.model.AuthTokenEntity
import ru.cinema.data.db.token.model.AuthTokenTable
import ru.cinema.data.db.user.model.UserTable
import ru.cinema.domain.auth.token.AuthTokenDbDataSource
import ru.cinema.domain.auth.token.model.AuthTokenInfo
import ru.cinema.domain.user.model.User
import java.util.*

class AuthTokenDbDataSourceImpl(
    override val db: Database
) : AuthTokenDbDataSource, DatabaseDataSource {
    override suspend fun addRefreshToken(userId: UUID, refreshTokenInfo: AuthTokenInfo) = dbQueryWithoutResult {
        AuthTokenEntity.new {
            refreshToken = refreshTokenInfo.token
            refreshTokenExpiresIn = refreshTokenInfo.tokenExpiresIn
            this.userId = EntityID(userId, UserTable)
        }
    }

    override suspend fun getRefreshToken(token: String): AuthTokenInfo? = dbQuery {
        AuthTokenEntity.find { AuthTokenTable.refreshToken eq token }.firstOrNull()?.toDomain()
    }

    @Suppress("UnnecessaryParentheses")
    override suspend fun deleteTokenForUser(token: String, userId: UUID) = dbQueryWithoutResult {
        AuthTokenTable.deleteWhere { (AuthTokenTable.refreshToken eq token) and (AuthTokenTable.userId eq userId) }
    }

    override suspend fun deleteTokensByTime(time: Long) = dbQueryWithoutResult {
        AuthTokenTable.deleteWhere { AuthTokenTable.refreshTokenExpiresIn less time }
    }

    override suspend fun getRefreshTokensByUserId(userId: UUID): List<AuthTokenInfo> = dbQuery {
        AuthTokenEntity.find { AuthTokenTable.userId eq userId }.map { it.toDomain() }
    }

    override suspend fun getUserByRefreshToken(token: String): User? = dbQuery {
        AuthTokenEntity.find { AuthTokenTable.refreshToken eq token }.firstOrNull()?.user?.toDomain()
    }

    @Suppress("UnnecessaryParentheses")
    override suspend fun logoutUser(userId: UUID) = dbQueryWithoutResult {
        AuthTokenTable.deleteWhere { (AuthTokenTable.userId eq userId) }
    }
}
