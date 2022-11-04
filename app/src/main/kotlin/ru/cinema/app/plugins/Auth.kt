package ru.cinema.app.plugins

import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.interfaces.Payload
import io.ktor.server.application.Application
import io.ktor.server.auth.AuthenticationConfig
import io.ktor.server.auth.authentication
import io.ktor.server.auth.jwt.jwt
import org.koin.core.qualifier.named
import org.koin.ktor.ext.inject
import ru.cinema.app.di.AuthModule
import ru.cinema.auth.TokenValidator
import ru.cinema.auth.model.AuthConstants
import ru.cinema.auth.model.UserPrincipal
import ru.cinema.domain.common.extensions.toUUIDOrNull
import java.util.*

fun Application.configureAuth() {
    val userTokenValidator by inject<TokenValidator>(named(AuthModule.USER_TOKEN_VALIDATOR))
    val adminTokenValidator by inject<TokenValidator>(named(AuthModule.ADMIN_TOKEN_VALIDATOR))

    authentication {
        userAuth(tokenValidator = userTokenValidator)
        adminAuth(tokenValidator = adminTokenValidator)
    }
}

private fun AuthenticationConfig.userAuth(tokenValidator: TokenValidator) {
    baseJwt(name = AuthConstants.USER_AUTH, verifier = tokenValidator.accessVerifier) { payload ->
        tokenValidator.getUserIdFromPayload(payload)?.toUUIDOrNull()
    }
    baseJwt(name = AuthConstants.USER_REFRESH_AUTH, verifier = tokenValidator.refreshVerifier) { payload ->
        tokenValidator.getUserIdFromPayload(payload)?.toUUIDOrNull()
    }
}

private fun AuthenticationConfig.adminAuth(tokenValidator: TokenValidator) {
    baseJwt(name = AuthConstants.ADMIN_AUTH, verifier = tokenValidator.accessVerifier) { payload ->
        tokenValidator.getUserIdFromPayload(payload)?.toUUIDOrNull()
    }
    baseJwt(name = AuthConstants.ADMIN_REFRESH_AUTH, verifier = tokenValidator.refreshVerifier) { payload ->
        tokenValidator.getUserIdFromPayload(payload)?.toUUIDOrNull()
    }
}

private fun AuthenticationConfig.baseJwt(name: String, verifier: JWTVerifier, userIdExtractor: (Payload) -> UUID?) {
    jwt(name = name) {
        verifier(verifier)
        validate { credentials ->
            val userId = userIdExtractor.invoke(credentials.payload)
            userId?.let { UserPrincipal(it) }
        }
    }
}
