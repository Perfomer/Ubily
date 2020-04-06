package com.example.feature.feed

import androidx.fragment.app.Fragment
import com.example.feature.feed.presentation.FeedFragment
import com.example.feature.feed.presentation.FeedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val BEAN_FRAGMENT_FEED = "FeedFragment"

val feedModule = module {
    single<Fragment>(named(BEAN_FRAGMENT_FEED)) { FeedFragment() }
    viewModel { FeedViewModel(get()) }
}