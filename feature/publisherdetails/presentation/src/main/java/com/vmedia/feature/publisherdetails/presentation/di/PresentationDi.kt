package com.vmedia.feature.publisherdetails.presentation.di

import androidx.fragment.app.Fragment
import com.vmedia.feature.publisherdetails.api.BEAN_FRAGMENT_PUBLISHERDETAILS
import com.vmedia.feature.publisherdetails.presentation.PublisherDetailsFragment
import com.vmedia.feature.publisherdetails.presentation.PublisherDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val featurePublisherDetailsPresentationModule = module {
    factory<Fragment>(named(BEAN_FRAGMENT_PUBLISHERDETAILS)) { PublisherDetailsFragment() }
    viewModel { PublisherDetailsViewModel(get()) }
}