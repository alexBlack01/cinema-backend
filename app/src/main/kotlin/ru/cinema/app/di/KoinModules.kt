package ru.cinema.app.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.cinema.api.auth.controller.AuthController
import ru.cinema.api.auth.controller.AuthControllerImpl
import ru.cinema.api.episodecomment.controller.CommentController
import ru.cinema.api.episodecomment.controller.CommentControllerImpl
import ru.cinema.api.movie.controller.MovieController
import ru.cinema.api.movie.controller.MovieControllerImpl
import ru.cinema.api.tag.controller.TagController
import ru.cinema.api.tag.controller.TagControllerImpl
import ru.cinema.api.user.controller.UserController
import ru.cinema.api.user.controller.UserControllerImpl
import ru.cinema.app.common.utils.SystemEnvVariablesUtil
import ru.cinema.auth.JwtTokenManagerImpl
import ru.cinema.auth.TokenValidator
import ru.cinema.data.db.common.Database
import ru.cinema.data.db.episodecomment.CommentDbDataSourceImpl
import ru.cinema.data.db.movie.MovieDbDataSourceImpl
import ru.cinema.data.db.tag.TagDbDataSourceImpl
import ru.cinema.data.db.token.AuthTokenDbDataSourceImpl
import ru.cinema.data.db.user.UserDbDataSourceImpl
import ru.cinema.domain.auth.LoginViaEmailUseCase
import ru.cinema.domain.auth.LoginViaEmailUseCaseImpl
import ru.cinema.domain.auth.LogoutUseCase
import ru.cinema.domain.auth.LogoutUseCaseImpl
import ru.cinema.domain.auth.RegisterViaEmailUseCase
import ru.cinema.domain.auth.RegisterViaEmailUseCaseImpl
import ru.cinema.domain.auth.token.AuthTokenDbDataSource
import ru.cinema.domain.auth.token.AuthTokenManager
import ru.cinema.domain.auth.token.RefreshTokenUseCase
import ru.cinema.domain.auth.token.RefreshTokenUseCaseImpl
import ru.cinema.domain.episode.GetEpisodesByMovieUseCase
import ru.cinema.domain.episode.GetEpisodesByMovieUseCaseImpl
import ru.cinema.domain.episodecomment.CommentDbDataSource
import ru.cinema.domain.episodecomment.GetCommentsByEpisodeUseCase
import ru.cinema.domain.episodecomment.GetCommentsByEpisodeUseCaseImpl
import ru.cinema.domain.episodecomment.PostCommentByEpisodeUseCase
import ru.cinema.domain.episodecomment.PostCommentByEpisodeUseCaseImpl
import ru.cinema.domain.movie.GetAllMoviesUseCase
import ru.cinema.domain.movie.GetAllMoviesUseCaseImpl
import ru.cinema.domain.movie.MovieDbDataSource
import ru.cinema.domain.tag.GetAllTagsUseCase
import ru.cinema.domain.tag.GetAllTagsUseCaseImpl
import ru.cinema.domain.tag.TagDbDataSource
import ru.cinema.domain.user.GetUserUseCase
import ru.cinema.domain.user.GetUserUseCaseImpl
import ru.cinema.domain.user.UserDbDataSource
import ru.cinema.domain.user.roles.RoleVerifier
import ru.cinema.domain.user.roles.RoleVerifierImpl

object KoinModules {
    private val dataBaseModule = module {
        single {
            Database.getHikariConfig(
                jdbcUrl = SystemEnvVariablesUtil.jdbcUrl,
                dbUser = SystemEnvVariablesUtil.databaseUser,
                dbPassword = SystemEnvVariablesUtil.databasePassword,
                poolSize = SystemEnvVariablesUtil.databaseConnectionCount
            )
        }
        single { Database.getHikariDataSource(config = get()) }
        single { Database.getDatabase(dataSource = get()) }
    }

    private val authModule = module {
        single<AuthTokenManager> {
            JwtTokenManagerImpl(
                secret = SystemEnvVariablesUtil.jwtSecret,
                issuer = SystemEnvVariablesUtil.host,
                accessTokenValidity = SystemEnvVariablesUtil.accessTokenValidity,
                refreshTokenValidity = SystemEnvVariablesUtil.refreshTokenValidity
            )
        }
        single { get<AuthTokenManager>() as TokenValidator }

        singleOf(::RoleVerifierImpl) bind RoleVerifier::class
    }

    private val dataSourceModule = module {
        factoryOf(::MovieDbDataSourceImpl) bind MovieDbDataSource::class
        factoryOf(::TagDbDataSourceImpl) bind TagDbDataSource::class
        factoryOf(::CommentDbDataSourceImpl) bind CommentDbDataSource::class
        factoryOf(::UserDbDataSourceImpl) bind UserDbDataSource::class
        factoryOf(::AuthTokenDbDataSourceImpl) bind AuthTokenDbDataSource::class
    }

    private val useCasesModule = module {
        factoryOf(::GetAllMoviesUseCaseImpl) bind GetAllMoviesUseCase::class
        factoryOf(::GetAllTagsUseCaseImpl) bind GetAllTagsUseCase::class
        factoryOf(::GetEpisodesByMovieUseCaseImpl) bind GetEpisodesByMovieUseCase::class
        factoryOf(::GetCommentsByEpisodeUseCaseImpl) bind GetCommentsByEpisodeUseCase::class
        factoryOf(::GetUserUseCaseImpl) bind GetUserUseCase::class
        factoryOf(::LoginViaEmailUseCaseImpl) bind LoginViaEmailUseCase::class
        factoryOf(::RefreshTokenUseCaseImpl) bind RefreshTokenUseCase::class
        factoryOf(::RegisterViaEmailUseCaseImpl) bind RegisterViaEmailUseCase::class
        factoryOf(::PostCommentByEpisodeUseCaseImpl) bind PostCommentByEpisodeUseCase::class
        factoryOf(::LogoutUseCaseImpl) bind LogoutUseCase::class
    }

    private val controllersModule = module {
        factoryOf(::MovieControllerImpl) bind MovieController::class
        factoryOf(::TagControllerImpl) bind TagController::class
        factoryOf(::CommentControllerImpl) bind CommentController::class
        factoryOf(::UserControllerImpl) bind UserController::class
        factoryOf(::AuthControllerImpl) bind AuthController::class
    }

    val appModules = module {
        includes(
            dataBaseModule,
            authModule,
            dataSourceModule,
            useCasesModule,
            controllersModule
        )
    }
}
