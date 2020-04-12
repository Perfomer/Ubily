package com.vmedia.feature.feed.presentation.di

import androidx.fragment.app.Fragment
import com.vmedia.core.navigation.BEAN_FRAGMENT_FEED
import com.vmedia.feature.feed.presentation.FeedFragment
import com.vmedia.feature.feed.presentation.FeedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val presentationModule = module {
    single<Fragment>(named(BEAN_FRAGMENT_FEED)) { FeedFragment() }
    viewModel { FeedViewModel(get()) }
}