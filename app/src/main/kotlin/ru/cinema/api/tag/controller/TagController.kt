package ru.cinema.api.tag.controller

import ru.cinema.domain.tag.model.Tag

interface TagController {
    suspend fun getAllTags(): List<Tag>
}
