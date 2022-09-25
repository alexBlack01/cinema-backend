package ru.cinema.domain.auth.model

data class LoginViaEmailParams(
    val email: String,
    val password: String
)
