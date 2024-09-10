package com.example.core.data.di

import com.example.core.data.auth.EncryptedSessionStorage
import com.example.core.data.network.HttpClientFactory
import com.example.core.domain.session.SessionStorage
import org.koin.dsl.module

val coreDataModule = module {

    single {
        HttpClientFactory().build()
    }

    single<SessionStorage> {
        EncryptedSessionStorage(get())
    }

}