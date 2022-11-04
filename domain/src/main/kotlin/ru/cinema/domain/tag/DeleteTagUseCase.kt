package ru.cinema.domain.tag

import ru.cinema.domain.common.error.TagNotFound
import ru.cinema.domain.common.usecase.UseCase
import java.util.UUID

interface DeleteTagUseCase : UseCase<UUID, Unit>

class DeleteTagUseCaseImpl(
    private val tagDbDataSource: TagDbDataSource
) : DeleteTagUseCase {

    /**
     * @param param is tag id
     */
    override suspend fun execute(param: UUID): Result<Unit> {
        tagDbDataSource.getTagById(param) ?: throw TagNotFound()

        return successResult(tagDbDataSource.deleteTagById(param))
    }
}
