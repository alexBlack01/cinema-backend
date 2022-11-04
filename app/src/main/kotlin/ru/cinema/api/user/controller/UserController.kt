package ru.cinema.api.user.controller

import io.ktor.http.content.MultiPartData
import ru.cinema.api.user.model.UserProfileBody
import ru.cinema.domain.user.model.UserProfile
import java.util.*

interface UserController {
    suspend fun getUserProfile(userId: UUID): UserProfile

    suspend fun postAvatarByUserId(userId: UUID, avatar: MultiPartData)

    suspend fun patchUserProfile(userId: UUID, userProfileBody: UserProfileBody): UserProfile

    suspend fun deleteUserByAdmin(userId: UUID)
}
