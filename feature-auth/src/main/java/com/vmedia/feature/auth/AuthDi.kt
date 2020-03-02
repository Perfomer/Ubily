package com.vmedia.feature.auth

import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.vmedia.feature.auth.data.AuthRepositoryImpl
import com.vmedia.feature.auth.data.CookieExtractor
import com.vmedia.feature.auth.data.browser.UnityWebViewSignInTask
import com.vmedia.feature.auth.domain.AuthInteractor
import com.vmedia.feature.auth.domain.AuthRepository
import com.vmedia.feature.auth.domain.SignInTask
import com.vmedia.feature.auth.presentation.AuthFragment
import com.vmedia.feature.auth.presentation.AuthViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

const val DI_FRAGMENT_AUTH = "AuthFragment"

val authModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), CookieExtractor, get()) }
    single { AuthInteractor(get()) }

    viewModel { AuthViewModel(get()) }

    factory<SignInTask> { UnityWebViewSignInTask(get()) }
    factory { WebView(androidApplication()) }
    factory<Fragment>(DI_FRAGMENT_AUTH) { AuthFragment() }
}