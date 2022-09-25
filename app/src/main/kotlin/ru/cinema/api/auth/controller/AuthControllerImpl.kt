package ru.cinema.api.auth.controller

import ru.cinema.api.auth.model.RegisterViaEmailBody
import ru.cinema.domain.auth.LoginViaEmailUseCase
import ru.cinema.domain.auth.LogoutUseCase
import ru.cinema.domain.auth.RegisterViaEmailUseCase
import ru.cinema.domain.auth.model.LoginViaEmailParams
import ru.cinema.domain.auth.token.RefreshTokenUseCase
import ru.cinema.domain.auth.token.model.AuthTokenPair
import java.util.*

class AuthControllerImpl(
    private val loginViaEmailUseCase: LoginViaEmailUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val registerViaEmailUseCase: RegisterViaEmailUseCase,
    private val logoutUseCase: LogoutUseCase
) : AuthController {
    override suspend fun registerViaEmail(registerBody: RegisterViaEmailBody): AuthTokenPair {
        return registerViaEmailUseCase(registerBody.toDomain()).getOrThrow()
    }

    override suspend fun loginViaEmail(email: String, password: String): AuthTokenPair {
        return loginViaEmailUseCase(LoginViaEmailParams(email, password)).getOrThrow()
    }

    override suspend fun logoutUser(userId: UUID) {
        logoutUseCase(userId).getOrThrow()
    }
    override suspend fun refreshToken(refreshToken: String): AuthTokenPair {
        return refreshTokenUseCase(refreshToken).getOrThrow()
    }
}
