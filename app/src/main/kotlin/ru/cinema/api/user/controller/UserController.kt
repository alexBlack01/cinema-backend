package ru.cinema.api.user.controller

import ru.cinema.domain.user.model.UserProfile
import java.util.*

interface UserController {
    suspend fun getUserProfile(userId: UUID): UserProfile
}
