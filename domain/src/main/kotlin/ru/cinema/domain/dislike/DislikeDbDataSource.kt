package ru.cinema.domain.dislike

import java.util.*

interface DislikeDbDataSource {

    suspend fun getUserDislikes(userId: UUID): List<UUID>

    suspend fun postDislikeByUser(userId: UUID, movieId: UUID)
}
