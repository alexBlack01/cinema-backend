package ru.cinema.domain.dislike

import ru.cinema.domain.common.usecase.UseCase
import java.util.UUID

interface PostDislikeByUserUseCase : UseCase<Pair<UUID, UUID>, Unit>

class PostDislikeByUserUseCaseImpl(
    private val dislikeDbDataSource: DislikeDbDataSource
) : PostDislikeByUserUseCase {

    /**
     * @param param.first is user id
     * @param param.second is movie id
     */
    override suspend fun execute(param: Pair<UUID, UUID>): Result<Unit> {
        return successResult(dislikeDbDataSource.postDislikeByUser(param.first, param.second))
    }
}
