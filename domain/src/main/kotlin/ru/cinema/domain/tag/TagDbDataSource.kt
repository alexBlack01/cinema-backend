package ru.cinema.domain.tag

import ru.cinema.domain.tag.model.EditTagForm
import ru.cinema.domain.tag.model.Tag
import ru.cinema.domain.tag.model.TagForm
import java.util.*

interface TagDbDataSource {
    suspend fun getAllTags(): List<Tag>
    suspend fun isTagExists(tagInfo: TagForm): Boolean
    suspend fun insertNewTag(tagInfo: TagForm): Tag
    suspend fun getTagById(tagId: UUID): Tag?
    suspend fun editTag(editTagForm: EditTagForm): Tag?
    suspend fun deleteTagById(tagId: UUID)
}
