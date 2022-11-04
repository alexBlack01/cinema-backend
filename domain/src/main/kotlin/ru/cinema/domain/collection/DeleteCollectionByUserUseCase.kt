package ru.cinema.domain.collection

import ru.cinema.domain.common.error.PermissionDenied
import ru.cinema.domain.common.usecase.UseCase
import java.util.UUID

interface DeleteCollectionByUserUseCase : UseCase<Pair<UUID, UUID>, Unit>

class DeleteCollectionByUserUseCaseImpl(
    private val collectionDbDataSource: CollectionDbDataSource
) : DeleteCollectionByUserUseCase {

    /**
     * @param param.first is user id
     * @param param.second is collection id
     */
    override suspend fun execute(param: Pair<UUID, UUID>): Result<Unit> {
        if (!collectionDbDataSource.isUserCollection(param.first, param.second)) throw PermissionDenied()

        return successResult(collectionDbDataSource.deleteCollectionByUser(param.second))
    }
}
