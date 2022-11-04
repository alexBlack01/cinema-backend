package ru.cinema.app.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.cinema.app.common.utils.SystemEnvVariablesUtil
import ru.cinema.auth.JwtTokenManagerImpl
import ru.cinema.auth.TokenValidator
import ru.cinema.domain.auth.token.AuthTokenManager
import ru.cinema.domain.user.roles.RoleVerifier
import ru.cinema.domain.user.roles.RoleVerifierImpl

object AuthModule {
    const val USER_TOKEN_MANAGER = "WEB_TOKEN_MANAGER"
    const val USER_TOKEN_VALIDATOR = "WEB_TOKEN_VALIDATOR"

    const val ADMIN_TOKEN_MANAGER = "ADMIN_TOKEN_MANAGER"
    const val ADMIN_TOKEN_VALIDATOR = "ADMIN_TOKEN_VALIDATOR"

    val module = module {
        single<AuthTokenManager>(named(USER_TOKEN_MANAGER)) {
            JwtTokenManagerImpl(
                accessSecret = SystemEnvVariablesUtil.jwtUserSecret,
                refreshSecret = SystemEnvVariablesUtil.jwtUserRefreshSecret,
                issuer = SystemEnvVariablesUtil.host,
                accessTokenValidity = SystemEnvVariablesUtil.accessTokenValidity,
                refreshTokenValidity = SystemEnvVariablesUtil.refreshTokenValidity
            )
        }
        single(named(USER_TOKEN_VALIDATOR)) { get<AuthTokenManager>(named(USER_TOKEN_MANAGER)) as TokenValidator }

        single<AuthTokenManager>(named(ADMIN_TOKEN_MANAGER)) {
            JwtTokenManagerImpl(
                accessSecret = SystemEnvVariablesUtil.jwtAdminSecret,
                refreshSecret = SystemEnvVariablesUtil.jwtAdminRefreshSecret,
                issuer = SystemEnvVariablesUtil.host.plus("/admin"),
                accessTokenValidity = SystemEnvVariablesUtil.accessTokenValidity,
                refreshTokenValidity = SystemEnvVariablesUtil.refreshTokenValidity
            )
        }
        single(named(ADMIN_TOKEN_VALIDATOR)) { get<AuthTokenManager>(named(ADMIN_TOKEN_MANAGER)) as TokenValidator }

        singleOf(::RoleVerifierImpl) bind RoleVerifier::class
    }
}
