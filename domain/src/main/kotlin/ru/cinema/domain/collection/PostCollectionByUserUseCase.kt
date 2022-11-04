package ru.cinema.domain.collection

import ru.cinema.domain.collection.model.Collection
import ru.cinema.domain.common.usecase.UseCase
import java.util.UUID

interface PostCollectionByUserUseCase : UseCase<Pair<UUID, String>, Collection>

class PostCollectionByUserUseCaseImpl(
    private val collectionDbDataSource: CollectionDbDataSource
) : PostCollectionByUserUseCase {

    /**
     * @param param.first is user id
     * @param param.second is collection name
     */
    override suspend fun execute(param: Pair<UUID, String>): Result<Collection> {
        return successResult(collectionDbDataSource.postCollectionByUser(param.first, param.second))
    }
}
