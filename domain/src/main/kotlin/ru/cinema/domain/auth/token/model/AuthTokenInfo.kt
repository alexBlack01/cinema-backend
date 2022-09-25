package ru.cinema.domain.auth.token.model

data class AuthTokenInfo(
    val token: String,
    val tokenExpiresIn: Long
)
