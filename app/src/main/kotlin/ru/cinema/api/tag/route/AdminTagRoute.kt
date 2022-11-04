package ru.cinema.api.tag.route

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable
import ru.cinema.api.common.serializations.UUIDSerializer
import java.util.*

@Serializable
@Resource("tags")
class NewTag

@Serializable
@Resource("tags/{tagId}")
class UpdateTag(@Serializable(with = UUIDSerializer::class) val tagId: UUID)
