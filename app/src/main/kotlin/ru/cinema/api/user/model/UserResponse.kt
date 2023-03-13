package ru.cinema.api.user.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ru.cinema.api.common.extensions.toResourceUrl
import ru.cinema.domain.user.model.UserProfile
import java.util.*

@Serializable
data class UserResponse(
    @Contextual
    val userId: UUID,
    val firstName: String,
    val lastName: String,
    val email: String,
    val avatar: String?
) {

    companion object {
        fun fromDomain(data: UserProfile) = UserResponse(
            userId = data.id,
            email = data.email,
            firstName = data.firstName,
            lastName = data.lastName,
            avatar = data.avatar?.toResourceUrl()
        )
    }
}
