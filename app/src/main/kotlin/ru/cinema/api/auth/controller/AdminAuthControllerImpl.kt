package ru.cinema.api.auth.controller

import ru.cinema.domain.auth.LoginViaEmailUseCase
import ru.cinema.domain.auth.model.LoginViaEmailParams
import ru.cinema.domain.auth.token.RefreshTokenUseCase
import ru.cinema.domain.auth.token.model.AuthTokenPair

class AdminAuthControllerImpl(
    private val loginViaEmailUseCase: LoginViaEmailUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase
) : AdminAuthController {

    override suspend fun loginViaEmail(email: String, password: String): AuthTokenPair {
        return loginViaEmailUseCase(LoginViaEmailParams(email, password)).getOrThrow()
    }

    override suspend fun refreshToken(refreshToken: String): AuthTokenPair {
        return refreshTokenUseCase(refreshToken).getOrThrow()
    }
}
