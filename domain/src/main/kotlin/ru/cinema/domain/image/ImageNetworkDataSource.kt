package ru.cinema.domain.image

interface ImageNetworkDataSource {

    suspend fun uploadFileToUploadcare(fileBytes: ByteArray, fileNameWithType: String): String?
}