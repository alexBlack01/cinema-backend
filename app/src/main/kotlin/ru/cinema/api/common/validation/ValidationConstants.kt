package ru.cinema.api.common.validation

object ValidationConstants {
    const val EMAIL_REGEX_PATTERN = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
        "\\@" +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
        "(" +
        "\\." +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
        ")+"

    // image size in Bytes
    const val MAX_IMAGE_SIZE = 5 * 1024 * 1024

    const val MAX_VIDEO_SIZE = 1024 * 1024 * 1024
}
