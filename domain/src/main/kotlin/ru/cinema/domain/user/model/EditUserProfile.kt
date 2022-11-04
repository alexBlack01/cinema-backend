package ru.cinema.domain.user.model

import java.util.*

data class EditUserProfile(
    val id: UUID,
    val firstName: String?,
    val lastName: String?,
    val password: String?
)
