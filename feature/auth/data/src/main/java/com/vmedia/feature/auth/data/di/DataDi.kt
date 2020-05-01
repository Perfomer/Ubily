package com.vmedia.feature.auth.data.di

import android.webkit.CookieManager
import com.vmedia.feature.auth.data.AuthRepositoryImpl
import com.vmedia.feature.auth.data.browser.UnityWebViewSignInTask
import com.vmedia.feature.auth.domain.AuthRepository
import com.vmedia.feature.auth.domain.SignInTask
import org.koin.dsl.module

val dataModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }
    factory<SignInTask> { UnityWebViewSignInTask(get()) }
    single { CookieManager.getInstance() }
}