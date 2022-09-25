package ru.cinema.domain.tag

import ru.cinema.domain.common.usecase.UseCaseWithoutParam
import ru.cinema.domain.tag.model.Tag

interface GetAllTagsUseCase : UseCaseWithoutParam<List<Tag>>

class GetAllTagsUseCaseImpl(
    private val tagDataSource: TagDbDataSource
) : GetAllTagsUseCase {

    override suspend fun execute(): Result<List<Tag>> {
        return successResult(tagDataSource.getAllTags())
    }
}
