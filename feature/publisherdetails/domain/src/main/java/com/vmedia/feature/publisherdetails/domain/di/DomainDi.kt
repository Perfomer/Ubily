package com.vmedia.feature.publisherdetails.domain.di

import com.vmedia.feature.publisherdetails.domain.PublisherDetailsInteractor
import org.koin.dsl.module

val featurePublisherDetailsDomainModule = module {
    single { PublisherDetailsInteractor(get()) }
}