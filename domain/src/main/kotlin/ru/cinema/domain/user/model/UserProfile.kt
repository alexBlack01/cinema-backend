package ru.cinema.domain.user.model

import java.util.*

data class UserProfile(
    val id: UUID,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String?
)
