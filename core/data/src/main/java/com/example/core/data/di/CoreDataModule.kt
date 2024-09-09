package com.example.core.data.di

import com.example.core.data.network.HttpClientFactory
import org.koin.dsl.module

val coreDataModule = module {

    single {
        HttpClientFactory().build()
    }

}