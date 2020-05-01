package com.vmedia.feature.auth.data

import android.webkit.CookieManager
import com.vmedia.core.common.pure.obj.creds.Credentials
import com.vmedia.core.common.pure.obj.creds.Token
import com.vmedia.core.data.datasource.CredentialsDataSource
import com.vmedia.feature.auth.data.util.clearCookies
import com.vmedia.feature.auth.data.util.getCookie
import com.vmedia.feature.auth.domain.AuthRepository
import com.vmedia.feature.auth.domain.SignInTask
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

internal class AuthRepositoryImpl(
    private val credentialsDataSource: CredentialsDataSource,
    private val cookieManager: CookieManager,
    private val signInTask: SignInTask
) : AuthRepository {

    override fun extractToken(): Single<Token> {
        return Single.fromCallable {
            Token(
                tokenValue = cookieManager.getCookie(
                    BuildConfig.NETWORK_COOKIE_BASE_URL,
                    BuildConfig.NETWORK_COOKIE_TOKEN
                )!!,
                session = cookieManager.getCookie(
                    BuildConfig.NETWORK_COOKIE_BASE_URL,
                    BuildConfig.NETWORK_COOKIE_SESSION
                )!!
            )
        }
    }

    override fun signIn(login: String, password: String): Completable {
        return clearCookies()
            .andThen(signInTask.signIn(login, password))
            .subscribeOn(AndroidSchedulers.mainThread())
    }

    override fun saveCredentials(credentials: Credentials): Completable {
        return credentialsDataSource.writeCredentials(credentials)
    }

    private fun clearCookies(): Completable {
        return Completable.fromAction(cookieManager::clearCookies)
    }

}