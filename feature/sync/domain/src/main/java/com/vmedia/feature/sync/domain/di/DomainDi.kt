package com.vmedia.feature.sync.domain.di

import com.vmedia.feature.sync.domain.SyncInteractor
import org.koin.dsl.module

val domainModule = module {
    single { SyncInteractor(get()) }
}