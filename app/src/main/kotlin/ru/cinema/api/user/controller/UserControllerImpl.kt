package ru.cinema.api.user.controller

import ru.cinema.domain.user.GetUserUseCase
import ru.cinema.domain.user.model.UserProfile
import java.util.*

class UserControllerImpl(
    private val getUserUseCase: GetUserUseCase
) : UserController {
    override suspend fun getUserProfile(userId: UUID): UserProfile {
        return getUserUseCase(userId).getOrThrow()
    }
}
