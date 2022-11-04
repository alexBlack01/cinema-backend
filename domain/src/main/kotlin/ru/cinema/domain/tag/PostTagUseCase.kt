package ru.cinema.domain.tag

import ru.cinema.domain.common.error.TagAlreadyExists
import ru.cinema.domain.common.usecase.UseCase
import ru.cinema.domain.tag.model.Tag
import ru.cinema.domain.tag.model.TagForm

interface PostTagUseCase : UseCase<TagForm, Tag>

class PostTagUseCaseImpl(
    private val tagDbDataSource: TagDbDataSource
) : PostTagUseCase {

    override suspend fun execute(param: TagForm): Result<Tag> {
        if (tagDbDataSource.isTagExists(param)) throw TagAlreadyExists()

        return successResult(tagDbDataSource.insertNewTag(param))
    }
}
