package ru.cinema.api.common.validation

import io.konform.validation.Constraint
import io.konform.validation.ValidationBuilder
import java.util.UUID

fun ValidationBuilder<String>.isNotBlank(): Constraint<String> = addConstraint(
    errorMessage = "must not consist only of spaces"
) { value -> value.isNotBlank() }

fun ValidationBuilder<String>.isEmail(): Constraint<String> = addConstraint(
    errorMessage = "must be valid email"
) { value ->
    ValidationConstants.EMAIL_REGEX_PATTERN.toRegex().matches(value)
}

fun ValidationBuilder<Int>.minValue(minValue: Int): Constraint<Int> = addConstraint(
    errorMessage = "must be greater than 0"
) { value -> value > minValue }

fun ValidationBuilder<List<UUID>>.isNotEmpty(): Constraint<List<UUID>> = addConstraint(
    errorMessage = "list must not be empty"
) { value -> value.isNotEmpty() }
