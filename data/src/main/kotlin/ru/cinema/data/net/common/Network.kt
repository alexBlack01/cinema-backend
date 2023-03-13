package ru.cinema.data.net.common

import com.uploadcare.api.Client


object Network {
    fun getUploadcareClient(publicKey: String, secretKey: String) = Client(publicKey, secretKey)
}
