package ru.cinema.api.cover.controller

import ru.cinema.domain.cover.model.Cover

interface CoverController {

    suspend fun getCover(): Cover
}
