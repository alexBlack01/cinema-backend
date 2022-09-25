package ru.cinema.domain.auth.token

import ru.cinema.domain.auth.token.model.AuthTokenPair
import ru.cinema.domain.user.roles.model.UserRole

interface AuthTokenManager {
    /**
     * Calculation of token expiration time
     *
     * @param tokenValidityTime is the token lifetime in seconds
     * @return Time when token expires in seconds UTC
     */
    fun calculateTokenExpiresIn(tokenValidityTime: Long): Long

    /**
     * Creating a new pair of access token and refresh token
     * @param userId is ID of the user for whom the token is generated
     * @return Information about tokens with their lifetimes - [AuthTokenPair]
     */
    fun generateNewTokenPair(userId: String, userRoles: List<UserRole>): AuthTokenPair
}
