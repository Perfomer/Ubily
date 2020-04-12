package com.vmedia.feature.feed.data.di

import com.vmedia.feature.feed.data.FeedRepositoryImpl
import com.vmedia.feature.feed.domain.FeedRepository
import org.koin.dsl.module

val dataModule = module {
    single<FeedRepository> { FeedRepositoryImpl(get()) }
}