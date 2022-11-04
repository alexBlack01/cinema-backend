package ru.cinema.data.local.image

import ru.cinema.domain.image.ImageLocalDataSource
import java.io.File

class ImageLocalDataSourceImpl : ImageLocalDataSource {

    override suspend fun saveImage(fileBytes: ByteArray, filePath: String, fileNameWithType: String) {
        File("$filePath/$fileNameWithType").writeBytes(fileBytes)
    }
}
