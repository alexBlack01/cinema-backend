package ru.cinema.domain.common.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
@Suppress("TooGenericExceptionCaught")
interface UseCaseWithoutParam<R> {
    suspend operator fun invoke(dispatcher: CoroutineDispatcher = Dispatchers.IO) = withContext(dispatcher) {
        try {
            execute()
        } catch (e: Exception) {
            e.printStackTrace()
            errorResult(e)
        }
    }

    suspend fun execute(): Result<R>
    fun errorResult(error: Throwable) = Result.failure<R>(error)
    fun successResult(data: R) = Result.success(data)
}
