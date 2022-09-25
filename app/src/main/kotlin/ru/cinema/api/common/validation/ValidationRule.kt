package ru.cinema.api.common.validation

import io.konform.validation.Constraint
import io.konform.validation.ValidationBuilder

fun ValidationBuilder<String>.isNotBlank(): Constraint<String> = addConstraint(
    errorMessage = "must not consist only of spaces"
) { value -> value.isNotBlank() }

fun ValidationBuilder<String>.isEmail(): Constraint<String> = addConstraint(
    errorMessage = "must be valid email"
) { value ->
    ValidationConstants.EMAIL_REGEX_PATTERN.toRegex().matches(value)
}
