package ru.cinema.domain.auth.token

import ru.cinema.domain.auth.token.model.AuthTokenPair
import ru.cinema.domain.common.error.InvalidRefreshToken
import ru.cinema.domain.common.error.RefreshTokenExpired
import ru.cinema.domain.common.usecase.UseCase
import ru.cinema.domain.common.utils.DateTimeUtils

interface RefreshTokenUseCase : UseCase<String, AuthTokenPair>

class RefreshTokenUseCaseImpl(
    private val authTokenManager: AuthTokenManager,
    private val authTokenDbDataSource: AuthTokenDbDataSource
) : RefreshTokenUseCase {

    /**
     * @param param is refresh token
     */
    override suspend fun execute(param: String): Result<AuthTokenPair> {
        val currentToken = authTokenDbDataSource.getRefreshToken(param)?.also {
            if (it.tokenExpiresIn <= DateTimeUtils.getNowSeconds()) throw RefreshTokenExpired()
        } ?: throw InvalidRefreshToken()

        val user = authTokenDbDataSource.getUserByRefreshToken(currentToken.token) ?: throw InvalidRefreshToken()
        val newTokenPair = authTokenManager.generateNewTokenPair(userId = user.id.toString())
        authTokenDbDataSource.deleteTokenForUser(token = currentToken.token, userId = user.id)
        authTokenDbDataSource.addRefreshToken(userId = user.id, refreshTokenInfo = newTokenPair.refreshTokenInfo)

        return successResult(newTokenPair)
    }
}
