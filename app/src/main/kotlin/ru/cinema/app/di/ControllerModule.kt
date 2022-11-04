package ru.cinema.app.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.cinema.api.auth.controller.AdminAuthController
import ru.cinema.api.auth.controller.AdminAuthControllerImpl
import ru.cinema.api.auth.controller.UserAuthController
import ru.cinema.api.auth.controller.UserAuthControllerImpl
import ru.cinema.api.chat.controller.ChatController
import ru.cinema.api.chat.controller.ChatControllerImpl
import ru.cinema.api.collection.controller.CollectionController
import ru.cinema.api.collection.controller.CollectionControllerImpl
import ru.cinema.api.cover.controller.CoverController
import ru.cinema.api.cover.controller.CoverControllerImpl
import ru.cinema.api.episode.controller.EpisodeController
import ru.cinema.api.episode.controller.EpisodeControllerImpl
import ru.cinema.api.episodecomment.controller.CommentController
import ru.cinema.api.episodecomment.controller.CommentControllerImpl
import ru.cinema.api.episodetime.controller.EpisodeTimeController
import ru.cinema.api.episodetime.controller.EpisodeTimeControllerImpl
import ru.cinema.api.movie.controller.MovieController
import ru.cinema.api.movie.controller.MovieControllerImpl
import ru.cinema.api.preference.controller.PreferenceController
import ru.cinema.api.preference.controller.PreferenceControllerImpl
import ru.cinema.api.tag.controller.TagController
import ru.cinema.api.tag.controller.TagControllerImpl
import ru.cinema.api.user.controller.UserController
import ru.cinema.api.user.controller.UserControllerImpl
import ru.cinema.api.userhistory.controller.HistoryController
import ru.cinema.api.userhistory.controller.HistoryControllerImpl

object ControllerModule {
    val module = module {
        factoryOf(::MovieControllerImpl) bind MovieController::class
        factoryOf(::TagControllerImpl) bind TagController::class
        factoryOf(::CommentControllerImpl) bind CommentController::class
        factoryOf(::UserControllerImpl) bind UserController::class
        factoryOf(::PreferenceControllerImpl) bind PreferenceController::class
        factoryOf(::ChatControllerImpl) bind ChatController::class
        factoryOf(::EpisodeTimeControllerImpl) bind EpisodeTimeController::class
        factoryOf(::CoverControllerImpl) bind CoverController::class
        factoryOf(::CollectionControllerImpl) bind CollectionController::class
        factoryOf(::HistoryControllerImpl) bind HistoryController::class
        factoryOf(::EpisodeControllerImpl) bind EpisodeController::class

        factory<UserAuthController> {
            UserAuthControllerImpl(
                registerViaEmailUseCase = get(named(UseCaseModule.REGISTER_USER_USE_CASE)),
                loginViaEmailUseCase = get(named(UseCaseModule.LOGIN_ACCOUNT_USER_USE_CASE)),
                refreshTokenUseCase = get(named(UseCaseModule.REFRESH_TOKEN_USER_USE_CASE))
            )
        }
        factory<AdminAuthController> {
            AdminAuthControllerImpl(
                loginViaEmailUseCase = get(named(UseCaseModule.LOGIN_ACCOUNT_ADMIN_USE_CASE)),
                refreshTokenUseCase = get(named(UseCaseModule.REFRESH_TOKEN_ADMIN_USE_CASE))
            )
        }
    }
}
