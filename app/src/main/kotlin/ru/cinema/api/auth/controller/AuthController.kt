package ru.cinema.api.auth.controller

import ru.cinema.api.auth.model.RegisterViaEmailBody
import ru.cinema.domain.auth.token.model.AuthTokenPair
import java.util.*

interface AuthController {
    suspend fun registerViaEmail(registerBody: RegisterViaEmailBody): AuthTokenPair
    suspend fun loginViaEmail(email: String, password: String): AuthTokenPair
    suspend fun logoutUser(userId: UUID)
    suspend fun refreshToken(refreshToken: String): AuthTokenPair
}
