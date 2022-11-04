package ru.cinema.api

import io.ktor.server.routing.Routing
import ru.cinema.api.auth.configureAuthRouting
import ru.cinema.api.chat.configureChatRouting
import ru.cinema.api.collection.configureCollectionsRouting
import ru.cinema.api.cover.configureCoverRouting
import ru.cinema.api.episode.configureEpisodesRouting
import ru.cinema.api.episodecomment.configureCommentsRouting
import ru.cinema.api.episodetime.configureEpisodeTimesRouting
import ru.cinema.api.movie.configureMoviesRouting
import ru.cinema.api.preference.configurePreferenceRouting
import ru.cinema.api.tag.configureTagsRouting
import ru.cinema.api.user.configureUserRouting
import ru.cinema.api.userhistory.configureHistoryRouting

fun Routing.configureApiRouting() {
    configureAuthRouting()
    configureTagsRouting()
    configureMoviesRouting()
    configureCommentsRouting()
    configureUserRouting()
    configurePreferenceRouting()
    configureChatRouting()
    configureEpisodeTimesRouting()
    configureCoverRouting()
    configureCollectionsRouting()
    configureHistoryRouting()
    configureEpisodesRouting()
}
