package ru.cinema.api.common.request

import io.konform.validation.Validation
import ru.cinema.api.common.extensions.validateAndThrow

interface MappedRequestBody<out T : Any> {
    fun toDomain(): T
}

interface MappedRequestBodyWithParam<out T : Any, in P> {
    fun toDomain(param: P): T
}

abstract class ValidatedBody<R> {
    protected abstract val validationRule: Validation<R>

    fun validate() {
        validationRule.validateAndThrow(this as R)
    }
}
