package ru.cinema.domain.auth.token

import ru.cinema.domain.auth.token.model.AuthTokenInfo
import ru.cinema.domain.user.model.User
import java.util.*

interface AuthTokenDbDataSource {
    suspend fun getRefreshTokensByUserId(userId: UUID): List<AuthTokenInfo>
    suspend fun getRefreshToken(token: String): AuthTokenInfo?
    suspend fun getUserByRefreshToken(token: String): User?
    suspend fun addRefreshToken(userId: UUID, refreshTokenInfo: AuthTokenInfo)
    suspend fun deleteTokenForUser(token: String, userId: UUID)
    suspend fun deleteTokensByTime(time: Long)
    suspend fun logoutUser(userId: UUID)
}
