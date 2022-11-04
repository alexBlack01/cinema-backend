package ru.cinema.domain.tag

import ru.cinema.domain.common.error.TagNotFound
import ru.cinema.domain.common.usecase.UseCase
import ru.cinema.domain.tag.model.EditTagForm
import ru.cinema.domain.tag.model.Tag

interface PatchTagUseCase : UseCase<EditTagForm, Tag>

class PatchTagUseCaseImpl(
    private val tagDbDataSource: TagDbDataSource
) : PatchTagUseCase {

    override suspend fun execute(param: EditTagForm): Result<Tag> {
        tagDbDataSource.getTagById(param.id) ?: throw TagNotFound()

        val newTag = tagDbDataSource.editTag(param) ?: throw TagNotFound()
        return successResult(newTag)
    }
}
