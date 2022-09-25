package ru.cinema.auth

import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import io.ktor.server.auth.jwt.JWTAuthChallengeFunction
import io.ktor.server.auth.jwt.JWTAuthenticationProvider
import io.ktor.server.request.path
import ru.cinema.domain.common.error.AccessTokenExpired
import ru.cinema.domain.common.error.InvalidAccessToken

fun JWTAuthenticationProvider.Config.getExtendedVerificationChallenge(
    refreshTokenPath: String,
    tokenValidator: TokenValidator
): JWTAuthChallengeFunction = { scheme, _ ->
    val error = call.request.headers["Authorization"].let { token ->
        if (token.isNullOrBlank()) {
            InvalidAccessToken(message = "Access token is null or empty")
        } else {
            val isRefreshRequest = call.request.path().contains(refreshTokenPath)
            processToken(
                token = token.replace(scheme, "").trim(),
                tokenValidator = tokenValidator,
                isRefreshRequest = isRefreshRequest
            )
        }
    }
    error?.let { throw it }
}

private fun processToken(token: String, tokenValidator: TokenValidator, isRefreshRequest: Boolean) = try {
    tokenValidator.verifier.verify(token)
    null
} catch (e: TokenExpiredException) {
    if (isRefreshRequest) null else AccessTokenExpired(message = e.message)
} catch (e: JWTVerificationException) {
    InvalidAccessToken(message = e.message)
}
