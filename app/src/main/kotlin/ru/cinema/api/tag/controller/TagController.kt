package ru.cinema.api.tag.controller

import ru.cinema.api.tag.model.TagBody
import ru.cinema.api.tag.model.TagEditBody
import ru.cinema.domain.tag.model.Tag
import java.util.*

interface TagController {
    suspend fun getAllTags(): List<Tag>
    suspend fun postNewTag(tagData: TagBody): Tag
    suspend fun patchTagById(tagId: UUID, editTagBody: TagEditBody): Tag
    suspend fun deleteTagById(tagId: UUID)
}
