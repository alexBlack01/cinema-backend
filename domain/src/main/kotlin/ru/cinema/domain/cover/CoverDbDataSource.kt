package ru.cinema.domain.cover

import ru.cinema.domain.cover.model.Cover

interface CoverDbDataSource {

    suspend fun getCover(): Cover?

    suspend fun updateCover(coverData: Cover)
}
