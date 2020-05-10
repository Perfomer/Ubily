package com.vmedia.feature.publisherdetails.domain.di

import com.vmedia.feature.publisherdetails.domain.PublisherDetailsInteractor
import org.koin.dsl.module

val domainModule = module {
    single { PublisherDetailsInteractor(get()) }
}