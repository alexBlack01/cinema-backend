package ru.cinema.api.user.model

import kotlinx.serialization.Serializable
import ru.cinema.domain.user.model.EditUserProfile
import java.util.*

@Serializable
data class UserProfileBody(
    val firstName: String? = null,
    val lastName: String? = null,
    val password: String? = null
) {
    fun toDomain(userId: UUID) = EditUserProfile(
        id = userId,
        firstName = firstName,
        lastName = lastName,
        password = password
    )
}
