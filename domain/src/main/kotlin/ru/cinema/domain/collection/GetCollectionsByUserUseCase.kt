package ru.cinema.domain.collection

import ru.cinema.domain.collection.model.Collection
import ru.cinema.domain.common.usecase.UseCase
import java.util.UUID

interface GetCollectionsByUserUseCase : UseCase<UUID, List<Collection>>

class GetCollectionsByUserUseCaseImpl(
    private val collectionDbDataSource: CollectionDbDataSource
) : GetCollectionsByUserUseCase {

    /**
     * @param param is user ID
     */
    override suspend fun execute(param: UUID): Result<List<Collection>> {
        return successResult(collectionDbDataSource.getCollectionsByUser(param))
    }
}
