package ru.cinema.api.tag.controller

import ru.cinema.domain.tag.GetAllTagsUseCase
import ru.cinema.domain.tag.model.Tag

class TagControllerImpl(
    private val getAllTagsUseCase: GetAllTagsUseCase
) : TagController {
    override suspend fun getAllTags(): List<Tag> {
        return getAllTagsUseCase().getOrThrow()
    }
}
