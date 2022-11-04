package ru.cinema.domain.image

import ru.cinema.domain.image.model.Image

interface AvatarDbDataSource {

    suspend fun addAvatar(avatarUrl: String): Image
}
