package ru.cinema.data.net.image

import com.uploadcare.api.Client
import com.uploadcare.api.File
import com.uploadcare.upload.FileUploader
import com.uploadcare.upload.UploadFailureException
import com.uploadcare.upload.Uploader
import ru.cinema.domain.image.ImageNetworkDataSource


class ImageNetworkDataSourceImpl(
    private val mediaService: Client
) : ImageNetworkDataSource {

    override suspend fun uploadFileToUploadcare(fileBytes: ByteArray, fileNameWithType: String): String? {
        val uploader: Uploader = FileUploader(mediaService, fileBytes, fileNameWithType)

        return try {
            val file: File = uploader.upload().save()
            file.fileId
        } catch (e: UploadFailureException) {
            null
        }
    }
}