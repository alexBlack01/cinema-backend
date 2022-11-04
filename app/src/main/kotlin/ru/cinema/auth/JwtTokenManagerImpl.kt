package ru.cinema.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.Payload
import ru.cinema.domain.auth.token.AuthTokenManager
import ru.cinema.domain.auth.token.model.AuthTokenInfo
import ru.cinema.domain.auth.token.model.AuthTokenPair
import ru.cinema.domain.common.utils.DateTimeUtils

class JwtTokenManagerImpl(
    accessSecret: String,
    refreshSecret: String,
    private val issuer: String,
    private val accessTokenValidity: Long,
    private val refreshTokenValidity: Long
) : TokenValidator, AuthTokenManager {
    private val accessAlgorithm = Algorithm.HMAC256(accessSecret.toByteArray())
    private val refreshAlgorithm = Algorithm.HMAC256(refreshSecret.toByteArray())

    override val accessVerifier: JWTVerifier = JWT
        .require(accessAlgorithm)
        .withIssuer(issuer)
        .build()

    override val refreshVerifier: JWTVerifier = JWT
        .require(refreshAlgorithm)
        .withIssuer(issuer)
        .build()

    override fun calculateTokenExpiresIn(tokenValidityTime: Long): Long =
        DateTimeUtils.getNowSeconds() + tokenValidityTime

    override fun generateNewTokenPair(userId: String): AuthTokenPair {
        val accessTokenExpiresIn = calculateTokenExpiresIn(accessTokenValidity)
        val refreshTokenExpiresIn = calculateTokenExpiresIn(refreshTokenValidity)

        return AuthTokenPair(
            accessTokenInfo = AuthTokenInfo(
                token = generateAccessToken(userId = userId, expiresIn = accessTokenExpiresIn),
                tokenExpiresIn = accessTokenExpiresIn
            ),
            refreshTokenInfo = AuthTokenInfo(
                token = generateRefreshToken(userId = userId, expiresIn = refreshTokenExpiresIn),
                tokenExpiresIn = refreshTokenExpiresIn
            )
        )
    }

    override fun getUserIdFromPayload(payload: Payload): String? = payload.claims?.get(KEY_CLAIM_USER)?.asString()

    private fun generateAccessToken(userId: String, expiresIn: Long): String = JWT.create()
        .withExpiresAt(DateTimeUtils.getDateFromSeconds(expiresIn))
        .withIssuer(issuer)
        .withClaim(KEY_CLAIM_USER, userId)
        .sign(accessAlgorithm)

    private fun generateRefreshToken(userId: String, expiresIn: Long): String = JWT.create()
        .withExpiresAt(DateTimeUtils.getDateFromSeconds(expiresIn))
        .withIssuer(issuer)
        .withClaim(KEY_CLAIM_USER, userId)
        .sign(refreshAlgorithm)

    private companion object {
        const val KEY_CLAIM_USER = "KEY_CLAIM_USER"
    }
}
