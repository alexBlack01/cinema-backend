package ru.cinema.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.Payload
import ru.cinema.domain.auth.token.AuthTokenManager
import ru.cinema.domain.auth.token.model.AuthTokenInfo
import ru.cinema.domain.auth.token.model.AuthTokenPair
import ru.cinema.domain.common.utils.DateTimeUtils
import ru.cinema.domain.user.roles.model.UserRole
import java.security.SecureRandom
import java.util.*

class JwtTokenManagerImpl(
    secret: String,
    private val issuer: String,
    private val accessTokenValidity: Long,
    private val refreshTokenValidity: Long
) : TokenValidator, AuthTokenManager {
    private val algorithm = Algorithm.HMAC256(secret.toByteArray())
    private val random = SecureRandom()

    override val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    override fun calculateTokenExpiresIn(tokenValidityTime: Long): Long =
        DateTimeUtils.getNowSeconds() + tokenValidityTime

    override fun generateNewTokenPair(userId: String, userRoles: List<UserRole>): AuthTokenPair {
        val accessTokenExpiresIn = calculateTokenExpiresIn(accessTokenValidity)
        val refreshTokenExpiresIn = calculateTokenExpiresIn(refreshTokenValidity)

        return AuthTokenPair(
            accessTokenInfo = AuthTokenInfo(
                token = generateAccessToken(userId = userId, expiresIn = accessTokenExpiresIn, userRoles = userRoles),
                tokenExpiresIn = accessTokenExpiresIn
            ),
            refreshTokenInfo = AuthTokenInfo(
                token = generateRefreshToken(),
                tokenExpiresIn = refreshTokenExpiresIn
            )
        )
    }

    override fun getUserIdFromPayload(payload: Payload): String? = payload.claims?.get(KEY_CLAIM_USER)?.asString()

    override fun getUserRolesFromPayload(payload: Payload): Set<UserRole> {
        return payload.claims?.get(KEY_CLAIM_ROLES)?.asList(String::class.java)
            ?.map { UserRole.valueOf(it) }?.toSet().orEmpty()
    }

    private fun generateAccessToken(userId: String, userRoles: List<UserRole>, expiresIn: Long): String = JWT.create()
        .withExpiresAt(DateTimeUtils.getDateFromSeconds(expiresIn))
        .withIssuer(issuer)
        .withClaim(KEY_CLAIM_USER, userId)
        .withClaim(KEY_CLAIM_ROLES, userRoles.map { it.name })
        .sign(algorithm)

    private fun generateRefreshToken(): String {
        val currentTimestampInBytes = DateTimeUtils.getNowMillis().toString().toByteArray()
        val bytes = ByteArray(REFRESH_LENGTH)
        random.nextBytes(bytes)
        return Base64.getEncoder().encodeToString(currentTimestampInBytes + bytes)
    }

    private companion object {
        const val REFRESH_LENGTH = 100
        const val KEY_CLAIM_USER = "KEY_CLAIM_USER"
        const val KEY_CLAIM_ROLES = "KEY_CLAIM_ROLES"
    }
}
