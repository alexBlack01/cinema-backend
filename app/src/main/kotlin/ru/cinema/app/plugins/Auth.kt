package ru.cinema.app.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.jwt
import org.koin.ktor.ext.inject
import ru.cinema.auth.TokenValidator
import ru.cinema.auth.getExtendedVerificationChallenge
import ru.cinema.auth.model.AuthConstants
import ru.cinema.auth.model.UserPrincipal
import ru.cinema.domain.common.extensions.toUUIDOrNull

fun Application.configureAuth() {
    val tokenValidator by inject<TokenValidator>()

    install(Authentication) {
        jwt {
            verifier(tokenValidator.verifier)
            challenge(
                getExtendedVerificationChallenge(
                    refreshTokenPath = AuthConstants.REFRESH_TOKEN_PATH,
                    tokenValidator = tokenValidator
                )
            )
            validate { credentials ->
                val userId = tokenValidator.getUserIdFromPayload(credentials.payload)?.toUUIDOrNull()
                userId?.let { UserPrincipal(it) }
            }
        }
    }
}
