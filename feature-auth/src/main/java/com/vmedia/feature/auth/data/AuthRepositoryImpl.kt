package com.vmedia.feature.auth.data

import com.vmedia.core.common.obj.creds.Credentials
import com.vmedia.core.common.obj.creds.Token
import com.vmedia.core.data.datasource.CredentialsDataSource
import com.vmedia.feature.auth.BuildConfig
import com.vmedia.feature.auth.domain.AuthRepository
import com.vmedia.feature.auth.domain.SignInTask
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

internal class AuthRepositoryImpl(
    private val credentialsDataSource: CredentialsDataSource,
    private val cookieExtractor: CookieExtractor,
    private val signInTask: SignInTask
) : AuthRepository {

    override fun extractToken(): Single<Token> {
        return Single.fromCallable {
            Token(
                tokenValue = cookieExtractor.getCookie(
                    BuildConfig.NETWORK_COOKIE_BASE_URL,
                    BuildConfig.NETWORK_COOKIE_TOKEN
                )!!,
                session = cookieExtractor.getCookie(
                    BuildConfig.NETWORK_COOKIE_BASE_URL,
                    BuildConfig.NETWORK_COOKIE_SESSION
                )!!
            )
        }
    }

    override fun signIn(login: String, password: String): Completable {
        return signInTask.signIn(login, password)
            .subscribeOn(AndroidSchedulers.mainThread())
    }

    override fun saveCredentials(credentials: Credentials): Completable {
        return credentialsDataSource.writeCredentials(credentials)
    }

}