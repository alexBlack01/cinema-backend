package ru.cinema.domain.cover

import ru.cinema.domain.common.error.CoverNotFound
import ru.cinema.domain.common.usecase.UseCaseWithoutParam
import ru.cinema.domain.cover.model.Cover

interface GetCoverUseCase : UseCaseWithoutParam<Cover>

class GetCoverUseCaseImpl(
    private val coverDbDataSource: CoverDbDataSource
) : GetCoverUseCase {

    override suspend fun execute(): Result<Cover> {
        return successResult(coverDbDataSource.getCover() ?: throw CoverNotFound())
    }
}
