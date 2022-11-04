package ru.cinema.domain.image

interface ImageLocalDataSource {

    suspend fun saveImage(fileBytes: ByteArray, filePath: String, fileNameWithType: String)
}
