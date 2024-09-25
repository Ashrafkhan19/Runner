package com.example.core.data.di

import com.example.core.data.auth.EncryptedSessionStorage
import com.example.core.data.network.HttpClientFactory
import com.example.core.data.run.OfflineFirstRunRepository
import com.example.core.domain.run.RunRepository
import com.example.core.domain.session.SessionStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {

    single {
        HttpClientFactory(get()).build()
    }

    single<SessionStorage> {
        EncryptedSessionStorage(get())
    }

    singleOf(::OfflineFirstRunRepository).bind<RunRepository>()

}