package ru.cinema.api.auth

import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.resources.post
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject
import ru.cinema.api.auth.controller.AdminAuthController
import ru.cinema.api.auth.controller.UserAuthController
import ru.cinema.api.auth.model.AuthTokenPairResponse
import ru.cinema.api.auth.model.LoginViaEmailBody
import ru.cinema.api.auth.model.RefreshTokenBody
import ru.cinema.api.auth.model.RegisterViaEmailBody
import ru.cinema.api.auth.route.Login
import ru.cinema.api.auth.route.RefreshToken
import ru.cinema.api.auth.route.Register
import ru.cinema.api.common.extensions.admin
import ru.cinema.api.common.extensions.respondSuccess
import ru.cinema.api.common.extensions.user
import ru.cinema.api.common.routing.RoutingRoleVerifier.verifyRolesByEmail
import ru.cinema.auth.model.AuthConstants
import ru.cinema.domain.auth.token.model.AuthTokenPair
import ru.cinema.domain.user.roles.model.UserRole

fun Route.configureAuthRouting() {
    val adminController by inject<AdminAuthController>()
    val userController by inject<UserAuthController>()

    authUser(
        onRegisterViaEmail = userController::registerViaEmail,
        onLoginViaEmail = userController::loginViaEmail,
        onRefreshToken = userController::refreshToken
    )
    authAdmin(
        onLoginViaEmail = adminController::loginViaEmail,
        onRefreshToken = adminController::refreshToken
    )
}

private fun Route.authUser(
    onRegisterViaEmail: suspend (RegisterViaEmailBody) -> AuthTokenPair,
    onLoginViaEmail: suspend (String, String) -> AuthTokenPair,
    onRefreshToken: suspend (String) -> AuthTokenPair
) = user {
    post<Register> {
        val body = call.receive<RegisterViaEmailBody>()
        val tokenPair = onRegisterViaEmail.invoke(body)
        call.respondSuccess(AuthTokenPairResponse.fromDomain(tokenPair))
    }

    post<Login> {
        val body = call.receive<LoginViaEmailBody>()
        val tokenPair = onLoginViaEmail.invoke(body.email, body.password)
        call.respondSuccess(AuthTokenPairResponse.fromDomain(tokenPair))
    }

    authenticate(AuthConstants.USER_REFRESH_AUTH) {
        post<RefreshToken> {
            val body = call.receive<RefreshTokenBody>()
            val tokenPair = onRefreshToken.invoke(body.refreshToken)
            call.respondSuccess(AuthTokenPairResponse.fromDomain(tokenPair))
        }
    }
}

private fun Route.authAdmin(
    onLoginViaEmail: suspend (String, String) -> AuthTokenPair,
    onRefreshToken: suspend (String) -> AuthTokenPair
) = admin {
    post<Login> {
        val body = call.receive<LoginViaEmailBody>()
        verifyRolesByEmail(body.email, setOf(UserRole.ADMIN))

        val tokenPair = onLoginViaEmail.invoke(body.email, body.password)
        call.respondSuccess(AuthTokenPairResponse.fromDomain(tokenPair))
    }

    authenticate(AuthConstants.ADMIN_REFRESH_AUTH) {
        post<RefreshToken> {
            val body = call.receive<RefreshTokenBody>()
            val tokenPair = onRefreshToken.invoke(body.refreshToken)
            call.respondSuccess(AuthTokenPairResponse.fromDomain(tokenPair))
        }
    }
}
