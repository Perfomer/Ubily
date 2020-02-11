package com.vmedia.feature.auth.data

import android.webkit.WebView
import com.vmedia.core.data.datasource.CredentialsDataSource
import com.vmedia.core.data.internal.Credentials
import com.vmedia.feature.auth.data.browser.unitySignIn
import com.vmedia.feature.auth.domain.AuthRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

internal class AuthRepositoryImpl(
    private val credentialsDataSource: CredentialsDataSource,
    private val cookieExtractor: CookieExtractor,
    private val webView: WebView
) : AuthRepository {

    override fun extractToken(): Single<String> {
        return Single.fromCallable {
            cookieExtractor.getCookie(URL_UNITY_ASSET_STORE, COOKIE_KHARMA_SESSION)
        }
    }

    override fun signIn(login: String, password: String): Completable {
        return webView.unitySignIn(login, password)
            .subscribeOn(AndroidSchedulers.mainThread())
    }

    override fun saveCredentials(credentials: Credentials): Completable {
        return credentialsDataSource.writeCredentials(credentials)
    }

    private companion object {

        private const val COOKIE_KHARMA_SESSION = "kharma_session"
        private const val URL_UNITY_ASSET_STORE = "https://assetstore.unity3d.com"

    }

}