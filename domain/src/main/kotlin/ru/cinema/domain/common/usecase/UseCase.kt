package ru.cinema.domain.common.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
@Suppress("TooGenericExceptionCaught")
interface UseCase<in P, R> {
    suspend operator fun invoke(param: P, dispatcher: CoroutineDispatcher = Dispatchers.IO) = withContext(dispatcher) {
        try {
            execute(param)
        } catch (e: Exception) {
            e.printStackTrace()
            errorResult(e)
        }
    }

    suspend fun execute(param: P): Result<R>

    fun errorResult(error: Throwable) = Result.failure<R>(error)
    fun successResult(data: R) = Result.success(data)
}
