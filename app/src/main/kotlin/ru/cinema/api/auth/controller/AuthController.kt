package ru.cinema.api.auth.controller

import ru.cinema.api.auth.model.RegisterViaEmailBody
import ru.cinema.domain.auth.token.model.AuthTokenPair

interface AuthController {
    suspend fun loginViaEmail(email: String, password: String): AuthTokenPair
    suspend fun refreshToken(refreshToken: String): AuthTokenPair
}

interface UserAuthController : AuthController {
    suspend fun registerViaEmail(registerBody: RegisterViaEmailBody): AuthTokenPair
}
interface AdminAuthController : AuthController
