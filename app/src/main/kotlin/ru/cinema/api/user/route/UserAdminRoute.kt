@file:Suppress("MatchingDeclarationName", "Filename")

package ru.cinema.api.user.route

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable
import ru.cinema.api.common.serializations.UUIDSerializer
import java.util.UUID

@Serializable
@Resource("profile/{userId}")
class DeleteProfile(@Serializable(with = UUIDSerializer::class) val userId: UUID)
