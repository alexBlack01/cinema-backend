package ru.cinema.api.cover.controller

import ru.cinema.domain.cover.GetCoverUseCase
import ru.cinema.domain.cover.model.Cover

class CoverControllerImpl(
    private val getCoverUseCase: GetCoverUseCase
) : CoverController {

    override suspend fun getCover(): Cover {
        return getCoverUseCase().getOrThrow()
    }
}
