package ru.cinema.domain.auth.model

data class RegisterViaEmailParams(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String
)
