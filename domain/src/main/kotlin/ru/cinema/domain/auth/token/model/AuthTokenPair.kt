package ru.cinema.domain.auth.token.model

data class AuthTokenPair(
    val accessTokenInfo: AuthTokenInfo,
    val refreshTokenInfo: AuthTokenInfo
)
