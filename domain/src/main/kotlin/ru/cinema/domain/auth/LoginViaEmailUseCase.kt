package ru.cinema.domain.auth

import ru.cinema.domain.auth.model.LoginViaEmailParams
import ru.cinema.domain.auth.token.AuthTokenDbDataSource
import ru.cinema.domain.auth.token.AuthTokenManager
import ru.cinema.domain.auth.token.model.AuthTokenPair
import ru.cinema.domain.common.error.InvalidUserCredentials
import ru.cinema.domain.common.usecase.UseCase
import ru.cinema.domain.common.utils.HashUtil
import ru.cinema.domain.user.UserDbDataSource

interface LoginViaEmailUseCase : UseCase<LoginViaEmailParams, AuthTokenPair>

class LoginViaEmailUseCaseImpl(
    private val userDbDataSource: UserDbDataSource,
    private val authTokenManager: AuthTokenManager,
    private val authTokenDbDataSource: AuthTokenDbDataSource
) : LoginViaEmailUseCase {

    override suspend fun execute(param: LoginViaEmailParams): Result<AuthTokenPair> {
        val user = userDbDataSource.getUserByEmail(param.email) ?: throw InvalidUserCredentials()
        val hashPassword = HashUtil.hash(param.password)
        if (hashPassword != user.password) throw InvalidUserCredentials()

        val userRoles = userDbDataSource.getUserRoles(userId = user.id)
        val tokenPair = authTokenManager.generateNewTokenPair(
            userId = user.id.toString(),
            userRoles = userRoles.toList()
        )
        authTokenDbDataSource.addRefreshToken(userId = user.id, refreshTokenInfo = tokenPair.refreshTokenInfo)
        return successResult(tokenPair)
    }
}
