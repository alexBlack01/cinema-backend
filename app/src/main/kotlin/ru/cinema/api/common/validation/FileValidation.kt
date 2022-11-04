package ru.cinema.api.common.validation

import ru.cinema.api.common.extensions.enumContains
import ru.cinema.domain.common.error.InvalidFileSize
import ru.cinema.domain.common.error.InvalidFileType
import ru.cinema.domain.movie.model.ContentForm

fun String.isImage() =
    if (!enumContains<ImageType>(this)) {
        throw InvalidFileType(
            message = "Format must be png, jpg or jpeg"
        )
    } else Unit

fun String.isVideo() =
    if (!enumContains<VideoType>(this)) {
        throw InvalidFileType(
            message = "Format must be avi, mp4, flv or wmv"
        )
    } else Unit

fun Int.isImageLengthCorrect() =
    if (this > ValidationConstants.MAX_IMAGE_SIZE) {
        throw InvalidFileSize(
            message = "Image size must be less than ${ValidationConstants.MAX_IMAGE_SIZE} B"
        )
    } else Unit

fun Int.isVideoLengthCorrect() =
    if (this > ValidationConstants.MAX_VIDEO_SIZE) {
        throw InvalidFileSize(
            message = "Image size must be less than ${ValidationConstants.MAX_VIDEO_SIZE} B"
        )
    } else Unit

fun ContentForm.isValidateImage() {
    this.fileType.isImage()
    this.fileBytes.size.isImageLengthCorrect()
}

fun ContentForm.isValidateVideo() {
    this.fileType.isVideo()
    this.fileBytes.size.isVideoLengthCorrect()
}
