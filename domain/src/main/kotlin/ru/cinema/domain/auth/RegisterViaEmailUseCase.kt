package ru.cinema.domain.auth

import ru.cinema.domain.auth.model.RegisterViaEmailParams
import ru.cinema.domain.auth.token.AuthTokenDbDataSource
import ru.cinema.domain.auth.token.AuthTokenManager
import ru.cinema.domain.auth.token.model.AuthTokenPair
import ru.cinema.domain.common.error.UserAlreadyExists
import ru.cinema.domain.common.usecase.UseCase
import ru.cinema.domain.common.utils.HashUtil
import ru.cinema.domain.user.UserDbDataSource
import ru.cinema.domain.user.roles.model.UserRole

interface RegisterViaEmailUseCase : UseCase<RegisterViaEmailParams, AuthTokenPair>

class RegisterViaEmailUseCaseImpl(
    private val userDbDataSource: UserDbDataSource,
    private val authTokenManager: AuthTokenManager,
    private val authTokenDbDataSource: AuthTokenDbDataSource
) : RegisterViaEmailUseCase {
    override suspend fun execute(param: RegisterViaEmailParams): Result<AuthTokenPair> {
        var user = userDbDataSource.getUserByEmail(param.email)
        if (user != null) throw UserAlreadyExists()

        val hashPassword = HashUtil.hash(param.password)
        user = userDbDataSource.addNewUser(param, hashPassword)

        userDbDataSource.addUserRole(user.id, UserRole.USER)
        val tokenPair = authTokenManager.generateNewTokenPair(
            userId = user.id.toString()
        )
        authTokenDbDataSource.addRefreshToken(userId = user.id, refreshTokenInfo = tokenPair.refreshTokenInfo)
        return successResult(tokenPair)
    }
}
