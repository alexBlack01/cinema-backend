package ru.cinema.api.user.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ru.cinema.api.common.extensions.toResourceUrl
import ru.cinema.domain.user.model.UserProfile
import java.util.*

@Serializable
data class UserResponse(
    @Contextual
    val id: UUID,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String?
) {

    companion object {
        fun fromDomain(data: UserProfile, baseUrl: String) = UserResponse(
            id = data.id,
            email = data.email,
            firstName = data.firstName,
            lastName = data.lastName,
            avatar = data.avatar?.toResourceUrl(baseUrl)
        )
    }
}
