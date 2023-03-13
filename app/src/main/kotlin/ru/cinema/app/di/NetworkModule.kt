package ru.cinema.app.di

import org.koin.dsl.module
import ru.cinema.app.common.utils.SystemEnvVariablesUtil
import ru.cinema.data.net.common.Network

object NetworkModule {

    val module = module {
        single {
            Network.getUploadcareClient(
                publicKey = SystemEnvVariablesUtil.publicKey,
                secretKey = SystemEnvVariablesUtil.privateKey
            )
        }
    }
}