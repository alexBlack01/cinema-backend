package ru.cinema.api.common.utils

import io.ktor.http.content.PartData
import io.ktor.http.content.streamProvider
import ru.cinema.domain.common.error.InvalidFileType
import ru.cinema.domain.movie.model.ContentForm

object ContentUtils {

    fun createContentForm(part: PartData.FileItem, url: String) =
        ContentForm(
            fileBytes = part.streamProvider().readBytes(),
            fileType = part.contentType?.contentSubtype ?: throw InvalidFileType(),
            filePath = url
        )
}
