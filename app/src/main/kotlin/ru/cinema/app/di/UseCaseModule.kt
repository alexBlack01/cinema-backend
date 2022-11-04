package ru.cinema.app.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.cinema.domain.auth.LoginViaEmailUseCase
import ru.cinema.domain.auth.LoginViaEmailUseCaseImpl
import ru.cinema.domain.auth.RegisterViaEmailUseCase
import ru.cinema.domain.auth.RegisterViaEmailUseCaseImpl
import ru.cinema.domain.auth.token.RefreshTokenUseCase
import ru.cinema.domain.auth.token.RefreshTokenUseCaseImpl
import ru.cinema.domain.chat.CheckChatExistUseCase
import ru.cinema.domain.chat.CheckChatExistUseCaseImpl
import ru.cinema.domain.chat.GetAllChatsByUserUseCase
import ru.cinema.domain.chat.GetAllChatsByUserUseCaseImpl
import ru.cinema.domain.chat.GetMessagesByChatUseCase
import ru.cinema.domain.chat.GetMessagesByChatUseCaseImpl
import ru.cinema.domain.chat.PostMessageByChatUseCase
import ru.cinema.domain.chat.PostMessageByChatUseCaseImpl
import ru.cinema.domain.collection.DeleteCollectionByUserUseCase
import ru.cinema.domain.collection.DeleteCollectionByUserUseCaseImpl
import ru.cinema.domain.collection.GetCollectionsByUserUseCase
import ru.cinema.domain.collection.GetCollectionsByUserUseCaseImpl
import ru.cinema.domain.collection.PostCollectionByUserUseCase
import ru.cinema.domain.collection.PostCollectionByUserUseCaseImpl
import ru.cinema.domain.collectionmovie.DeleteMovieFromCollectionUseCase
import ru.cinema.domain.collectionmovie.DeleteMovieFromCollectionUseCaseImpl
import ru.cinema.domain.collectionmovie.GetMoviesByCollectionUseCase
import ru.cinema.domain.collectionmovie.GetMoviesByCollectionUseCaseImpl
import ru.cinema.domain.collectionmovie.PostMovieToCollectionUseCase
import ru.cinema.domain.collectionmovie.PostMovieToCollectionUseCaseImpl
import ru.cinema.domain.cover.GetCoverUseCase
import ru.cinema.domain.cover.GetCoverUseCaseImpl
import ru.cinema.domain.dislike.PostDislikeByUserUseCase
import ru.cinema.domain.dislike.PostDislikeByUserUseCaseImpl
import ru.cinema.domain.episode.DeleteEpisodeUseCase
import ru.cinema.domain.episode.DeleteEpisodeUseCaseImpl
import ru.cinema.domain.episode.GetEpisodesByMovieUseCase
import ru.cinema.domain.episode.GetEpisodesByMovieUseCaseImpl
import ru.cinema.domain.episode.PatchEpisodeUseCase
import ru.cinema.domain.episode.PatchEpisodeUseCaseImpl
import ru.cinema.domain.episode.PostEpisodeByMovieUseCase
import ru.cinema.domain.episode.PostEpisodeByMovieUseCaseImpl
import ru.cinema.domain.episode.PutEpisodeFilesUseCase
import ru.cinema.domain.episode.PutEpisodeFilesUseCaseImpl
import ru.cinema.domain.episodecomment.DeleteCommentByAdminUseCase
import ru.cinema.domain.episodecomment.DeleteCommentByAdminUseCaseImpl
import ru.cinema.domain.episodecomment.GetCommentsByEpisodeUseCase
import ru.cinema.domain.episodecomment.GetCommentsByEpisodeUseCaseImpl
import ru.cinema.domain.episodecomment.PostCommentByEpisodeUseCase
import ru.cinema.domain.episodecomment.PostCommentByEpisodeUseCaseImpl
import ru.cinema.domain.episodetime.GetEpisodeTimeByUserUseCase
import ru.cinema.domain.episodetime.GetEpisodeTimeByUserUseCaseImpl
import ru.cinema.domain.episodetime.GetHistoryByUserUseCase
import ru.cinema.domain.episodetime.GetHistoryByUserUseCaseImpl
import ru.cinema.domain.episodetime.PostEpisodeTimeByUserUseCase
import ru.cinema.domain.episodetime.PostEpisodeTimeByUserUseCaseImpl
import ru.cinema.domain.movie.DeleteMovieUseCase
import ru.cinema.domain.movie.DeleteMovieUseCaseImpl
import ru.cinema.domain.movie.GetAllMoviesUseCase
import ru.cinema.domain.movie.GetAllMoviesUseCaseImpl
import ru.cinema.domain.movie.PatchMovieUseCase
import ru.cinema.domain.movie.PatchMovieUseCaseImpl
import ru.cinema.domain.movie.PostMovieUseCase
import ru.cinema.domain.movie.PostMovieUseCaseImpl
import ru.cinema.domain.movie.PutMovieImagesUseCase
import ru.cinema.domain.movie.PutMovieImagesUseCaseImpl
import ru.cinema.domain.preference.GetPreferencesByUserUseCase
import ru.cinema.domain.preference.GetPreferencesByUserUseCaseImpl
import ru.cinema.domain.preference.PutPreferenceByUserUseCase
import ru.cinema.domain.preference.PutPreferenceByUserUseCaseImpl
import ru.cinema.domain.tag.DeleteTagUseCase
import ru.cinema.domain.tag.DeleteTagUseCaseImpl
import ru.cinema.domain.tag.GetAllTagsUseCase
import ru.cinema.domain.tag.GetAllTagsUseCaseImpl
import ru.cinema.domain.tag.PatchTagUseCase
import ru.cinema.domain.tag.PatchTagUseCaseImpl
import ru.cinema.domain.tag.PostTagUseCase
import ru.cinema.domain.tag.PostTagUseCaseImpl
import ru.cinema.domain.user.DeleteUserByAdminUseCase
import ru.cinema.domain.user.DeleteUserByAdminUseCaseImpl
import ru.cinema.domain.user.GetUserUseCase
import ru.cinema.domain.user.GetUserUseCaseImpl
import ru.cinema.domain.user.PatchUserUseCase
import ru.cinema.domain.user.PatchUserUseCaseImpl
import ru.cinema.domain.user.PostAvatarByUserIdUseCase
import ru.cinema.domain.user.PostAvatarByUserIdUseCaseImpl

object UseCaseModule {
    const val REGISTER_USER_USE_CASE = "REGISTER_USER_USE_CASE"
    const val LOGIN_ACCOUNT_USER_USE_CASE = "LOGIN_ACCOUNT_USER_USE_CASE"
    const val LOGIN_ACCOUNT_ADMIN_USE_CASE = "LOGIN_ACCOUNT_ADMIN_USE_CASE"
    const val REFRESH_TOKEN_USER_USE_CASE = "REFRESH_TOKEN_USER_USE_CASE"
    const val REFRESH_TOKEN_ADMIN_USE_CASE = "REFRESH_TOKEN_ADMIN_USE_CASE"

    val module = module {
        factoryOf(::GetAllMoviesUseCaseImpl) bind GetAllMoviesUseCase::class
        factoryOf(::GetAllTagsUseCaseImpl) bind GetAllTagsUseCase::class
        factoryOf(::GetEpisodesByMovieUseCaseImpl) bind GetEpisodesByMovieUseCase::class
        factoryOf(::GetCommentsByEpisodeUseCaseImpl) bind GetCommentsByEpisodeUseCase::class
        factoryOf(::GetUserUseCaseImpl) bind GetUserUseCase::class
        factoryOf(::RegisterViaEmailUseCaseImpl) bind RegisterViaEmailUseCase::class
        factoryOf(::PostCommentByEpisodeUseCaseImpl) bind PostCommentByEpisodeUseCase::class
        factoryOf(::PostAvatarByUserIdUseCaseImpl) bind PostAvatarByUserIdUseCase::class
        factoryOf(::GetAllChatsByUserUseCaseImpl) bind GetAllChatsByUserUseCase::class
        factoryOf(::GetMessagesByChatUseCaseImpl) bind GetMessagesByChatUseCase::class
        factoryOf(::PostMessageByChatUseCaseImpl) bind PostMessageByChatUseCase::class
        factoryOf(::CheckChatExistUseCaseImpl) bind CheckChatExistUseCase::class
        factoryOf(::PatchUserUseCaseImpl) bind PatchUserUseCase::class
        factoryOf(::GetPreferencesByUserUseCaseImpl) bind GetPreferencesByUserUseCase::class
        factoryOf(::PutPreferenceByUserUseCaseImpl) bind PutPreferenceByUserUseCase::class
        factoryOf(::GetCoverUseCaseImpl) bind GetCoverUseCase::class
        factoryOf(::GetCollectionsByUserUseCaseImpl) bind GetCollectionsByUserUseCase::class
        factoryOf(::PostCollectionByUserUseCaseImpl) bind PostCollectionByUserUseCase::class
        factoryOf(::DeleteCollectionByUserUseCaseImpl) bind DeleteCollectionByUserUseCase::class
        factoryOf(::GetEpisodeTimeByUserUseCaseImpl) bind GetEpisodeTimeByUserUseCase::class
        factoryOf(::PostEpisodeTimeByUserUseCaseImpl) bind PostEpisodeTimeByUserUseCase::class
        factoryOf(::GetMoviesByCollectionUseCaseImpl) bind GetMoviesByCollectionUseCase::class
        factoryOf(::PostMovieToCollectionUseCaseImpl) bind PostMovieToCollectionUseCase::class
        factoryOf(::DeleteMovieFromCollectionUseCaseImpl) bind DeleteMovieFromCollectionUseCase::class
        factoryOf(::GetHistoryByUserUseCaseImpl) bind GetHistoryByUserUseCase::class
        factoryOf(::PostDislikeByUserUseCaseImpl) bind PostDislikeByUserUseCase::class
        factoryOf(::PostMovieUseCaseImpl) bind PostMovieUseCase::class
        factoryOf(::DeleteMovieUseCaseImpl) bind DeleteMovieUseCase::class
        factoryOf(::PutMovieImagesUseCaseImpl) bind PutMovieImagesUseCase::class
        factoryOf(::PatchMovieUseCaseImpl) bind PatchMovieUseCase::class
        factoryOf(::PostTagUseCaseImpl) bind PostTagUseCase::class
        factoryOf(::PatchTagUseCaseImpl) bind PatchTagUseCase::class
        factoryOf(::DeleteTagUseCaseImpl) bind DeleteTagUseCase::class
        factoryOf(::DeleteCommentByAdminUseCaseImpl) bind DeleteCommentByAdminUseCase::class
        factoryOf(::DeleteUserByAdminUseCaseImpl) bind DeleteUserByAdminUseCase::class
        factoryOf(::DeleteEpisodeUseCaseImpl) bind DeleteEpisodeUseCase::class
        factoryOf(::PostEpisodeByMovieUseCaseImpl) bind PostEpisodeByMovieUseCase::class
        factoryOf(::PatchEpisodeUseCaseImpl) bind PatchEpisodeUseCase::class
        factoryOf(::PutEpisodeFilesUseCaseImpl) bind PutEpisodeFilesUseCase::class

        factory<RegisterViaEmailUseCase>(named(REGISTER_USER_USE_CASE)) {
            RegisterViaEmailUseCaseImpl(
                authTokenManager = get(named(AuthModule.USER_TOKEN_MANAGER)),
                userDbDataSource = get(),
                authTokenDbDataSource = get()
            )
        }
        factory<LoginViaEmailUseCase>(named(LOGIN_ACCOUNT_USER_USE_CASE)) {
            LoginViaEmailUseCaseImpl(
                authTokenManager = get(named(AuthModule.USER_TOKEN_MANAGER)),
                userDbDataSource = get(),
                authTokenDbDataSource = get()
            )
        }
        factory<LoginViaEmailUseCase>(named(LOGIN_ACCOUNT_ADMIN_USE_CASE)) {
            LoginViaEmailUseCaseImpl(
                authTokenManager = get(named(AuthModule.ADMIN_TOKEN_MANAGER)),
                userDbDataSource = get(),
                authTokenDbDataSource = get()
            )
        }
        factory<RefreshTokenUseCase>(named(REFRESH_TOKEN_USER_USE_CASE)) {
            RefreshTokenUseCaseImpl(
                authTokenManager = get(named(AuthModule.USER_TOKEN_MANAGER)),
                authTokenDbDataSource = get()
            )
        }
        factory<RefreshTokenUseCase>(named(REFRESH_TOKEN_ADMIN_USE_CASE)) {
            RefreshTokenUseCaseImpl(
                authTokenManager = get(named(AuthModule.ADMIN_TOKEN_MANAGER)),
                authTokenDbDataSource = get()
            )
        }
    }
}
