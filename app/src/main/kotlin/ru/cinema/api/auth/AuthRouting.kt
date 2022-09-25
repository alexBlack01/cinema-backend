package ru.cinema.api.auth

import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.resources.post
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject
import ru.cinema.api.auth.controller.AuthController
import ru.cinema.api.auth.model.AuthTokenPairResponse
import ru.cinema.api.auth.model.LoginViaEmailBody
import ru.cinema.api.auth.model.RefreshTokenBody
import ru.cinema.api.auth.model.RegisterViaEmailBody
import ru.cinema.api.auth.route.Login
import ru.cinema.api.auth.route.Logout
import ru.cinema.api.auth.route.RefreshToken
import ru.cinema.api.auth.route.Register
import ru.cinema.api.common.extensions.principalOrThrow
import ru.cinema.api.common.extensions.respondSuccess
import ru.cinema.api.common.extensions.respondSuccessNoContent
import ru.cinema.auth.model.UserPrincipal

fun Route.configureAuthRouting() {
    val controller by inject<AuthController>()

    post<Register> {
        val body = call.receive<RegisterViaEmailBody>()
        val tokenPair = controller.registerViaEmail(body)
        call.respondSuccess(AuthTokenPairResponse.fromDomain(tokenPair))
    }

    post<Login> {
        val body = call.receive<LoginViaEmailBody>()
        val tokenPair = controller.loginViaEmail(body.email, body.password)
        call.respondSuccess(AuthTokenPairResponse.fromDomain(tokenPair))
    }

    authenticate {
        post<RefreshToken> {
            val body = call.receive<RefreshTokenBody>()
            val tokenPair = controller.refreshToken(body.refreshToken)
            call.respondSuccess(AuthTokenPairResponse.fromDomain(tokenPair))
        }

        post<Logout> {
            val userId = call.principalOrThrow<UserPrincipal>().userId
            controller.logoutUser(userId)
            call.respondSuccessNoContent()
        }
    }
}
