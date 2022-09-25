package ru.cinema.domain.common.utils

import java.security.MessageDigest

object HashUtil {
    private const val ALGORITHM_TYPE = "SHA-256"
    private const val HEX_CHARS = "0123456789ABCDEF"

    fun hash(input: String): String {
        require(input.isNotBlank())
        return hashString(hashString(input))
    }

    @Suppress("MagicNumber")
    private fun hashString(input: String): String {
        val md = MessageDigest.getInstance(ALGORITHM_TYPE).apply {
            reset()
        }

        val bytes = md.digest(input.toByteArray())
        val result = StringBuilder(bytes.size * 2)

        bytes.forEach {
            val i = it.toInt()
            result.append(HEX_CHARS[i shr 4 and 0x0f])
            result.append(HEX_CHARS[i and 0x0f])
        }

        return result.toString()
    }
}
