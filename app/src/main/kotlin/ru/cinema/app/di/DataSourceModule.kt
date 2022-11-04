package ru.cinema.app.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.cinema.data.db.chat.ChatDbDataSourceImpl
import ru.cinema.data.db.collection.CollectionDbDataSourceImpl
import ru.cinema.data.db.collection.CollectionMovieDbDataSourceImpl
import ru.cinema.data.db.cover.CoverDbDataSourceImpl
import ru.cinema.data.db.dislike.DislikeDbDataSourceImpl
import ru.cinema.data.db.episode.EpisodeDbDataSourceImpl
import ru.cinema.data.db.episodecomment.CommentDbDataSourceImpl
import ru.cinema.data.db.image.AvatarDbDataSourceImpl
import ru.cinema.data.db.movie.MovieDbDataSourceImpl
import ru.cinema.data.db.preference.PreferenceDbDataSourceImpl
import ru.cinema.data.db.tag.TagDbDataSourceImpl
import ru.cinema.data.db.token.AuthTokenDbDataSourceImpl
import ru.cinema.data.db.user.UserDbDataSourceImpl
import ru.cinema.data.db.userhistory.EpisodeTimeDbDataSourceImpl
import ru.cinema.data.local.image.ImageLocalDataSourceImpl
import ru.cinema.domain.auth.token.AuthTokenDbDataSource
import ru.cinema.domain.chat.ChatDbDataSource
import ru.cinema.domain.collection.CollectionDbDataSource
import ru.cinema.domain.collectionmovie.CollectionMovieDbDataSource
import ru.cinema.domain.cover.CoverDbDataSource
import ru.cinema.domain.dislike.DislikeDbDataSource
import ru.cinema.domain.episode.EpisodeDbDataSource
import ru.cinema.domain.episodecomment.CommentDbDataSource
import ru.cinema.domain.episodetime.EpisodeTimeDbDataSource
import ru.cinema.domain.image.AvatarDbDataSource
import ru.cinema.domain.image.ImageLocalDataSource
import ru.cinema.domain.movie.MovieDbDataSource
import ru.cinema.domain.preference.PreferenceDbDataSource
import ru.cinema.domain.tag.TagDbDataSource
import ru.cinema.domain.user.UserDbDataSource

object DataSourceModule {
    val module = module {
        factoryOf(::MovieDbDataSourceImpl) bind MovieDbDataSource::class
        factoryOf(::TagDbDataSourceImpl) bind TagDbDataSource::class
        factoryOf(::CommentDbDataSourceImpl) bind CommentDbDataSource::class
        factoryOf(::UserDbDataSourceImpl) bind UserDbDataSource::class
        factoryOf(::AuthTokenDbDataSourceImpl) bind AuthTokenDbDataSource::class
        factoryOf(::AvatarDbDataSourceImpl) bind AvatarDbDataSource::class
        factoryOf(::ImageLocalDataSourceImpl) bind ImageLocalDataSource::class
        factoryOf(::PreferenceDbDataSourceImpl) bind PreferenceDbDataSource::class
        factoryOf(::CollectionDbDataSourceImpl) bind CollectionDbDataSource::class
        factoryOf(::CollectionMovieDbDataSourceImpl) bind CollectionMovieDbDataSource::class
        factoryOf(::CoverDbDataSourceImpl) bind CoverDbDataSource::class
        factoryOf(::EpisodeTimeDbDataSourceImpl) bind EpisodeTimeDbDataSource::class
        factoryOf(::EpisodeDbDataSourceImpl) bind EpisodeDbDataSource::class
        factoryOf(::DislikeDbDataSourceImpl) bind DislikeDbDataSource::class
        factoryOf(::ChatDbDataSourceImpl) bind ChatDbDataSource::class
    }
}
