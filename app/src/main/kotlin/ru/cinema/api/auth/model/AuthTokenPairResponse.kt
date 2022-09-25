package ru.cinema.api.auth.model

import kotlinx.serialization.Serializable
import ru.cinema.domain.auth.token.model.AuthTokenPair

@Serializable
data class AuthTokenPairResponse(
    val accessToken: String,
    val accessTokenExpiresIn: Long,
    val refreshToken: String,
    val refreshTokenExpiresIn: Long
) {
    companion object {
        fun fromDomain(data: AuthTokenPair) = AuthTokenPairResponse(
            accessToken = data.accessTokenInfo.token,
            accessTokenExpiresIn = data.accessTokenInfo.tokenExpiresIn,
            refreshToken = data.refreshTokenInfo.token,
            refreshTokenExpiresIn = data.refreshTokenInfo.tokenExpiresIn
        )
    }
}
