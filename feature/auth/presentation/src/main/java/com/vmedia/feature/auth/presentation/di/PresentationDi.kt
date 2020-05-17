package com.vmedia.feature.auth.presentation.di

import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.vmedia.feature.auth.api.BEAN_FRAGMENT_AUTH
import com.vmedia.feature.auth.presentation.AuthFragment
import com.vmedia.feature.auth.presentation.AuthViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val featureAuthPresentationModule = module {
    viewModel { AuthViewModel(get()) }
    factory { WebView(androidApplication()) }
    factory<Fragment>(named(BEAN_FRAGMENT_AUTH)) { AuthFragment() }
}