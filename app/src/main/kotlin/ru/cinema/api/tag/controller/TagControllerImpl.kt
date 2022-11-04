package ru.cinema.api.tag.controller

import ru.cinema.api.tag.model.TagBody
import ru.cinema.api.tag.model.TagEditBody
import ru.cinema.domain.tag.DeleteTagUseCase
import ru.cinema.domain.tag.GetAllTagsUseCase
import ru.cinema.domain.tag.PatchTagUseCase
import ru.cinema.domain.tag.PostTagUseCase
import ru.cinema.domain.tag.model.Tag
import java.util.*

class TagControllerImpl(
    private val getAllTagsUseCase: GetAllTagsUseCase,
    private val postTagUseCase: PostTagUseCase,
    private val patchTagUseCase: PatchTagUseCase,
    private val deleteTagUseCase: DeleteTagUseCase
) : TagController {
    override suspend fun getAllTags(): List<Tag> {
        return getAllTagsUseCase().getOrThrow()
    }

    override suspend fun postNewTag(tagData: TagBody): Tag {
        return postTagUseCase(tagData.toDomain()).getOrThrow()
    }

    override suspend fun patchTagById(tagId: UUID, editTagBody: TagEditBody): Tag {
        return patchTagUseCase(editTagBody.toDomain(tagId)).getOrThrow()
    }

    override suspend fun deleteTagById(tagId: UUID) {
        deleteTagUseCase(tagId).getOrThrow()
    }
}
