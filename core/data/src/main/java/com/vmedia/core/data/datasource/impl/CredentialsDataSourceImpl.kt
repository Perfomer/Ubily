package com.vmedia.core.data.datasource.impl

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.vmedia.core.common.android.util.preferences.encryptedValue
import com.vmedia.core.common.pure.obj.creds.Credentials
import com.vmedia.core.common.pure.obj.creds.Token
import com.vmedia.core.data.datasource.CredentialsDataSource
import com.vmedia.core.network.datasource.MutableNetworkCredentialsProvider
import io.reactivex.Completable
import io.reactivex.Single

@SuppressLint("ApplySharedPref")
internal class CredentialsDataSourceImpl(
    private val preferences: SharedPreferences,
    private val credentialsProvider: MutableNetworkCredentialsProvider
) : CredentialsDataSource {

    private var login: String by preferences.encryptedValue(KEY_LOGIN)
    private var password: String by preferences.encryptedValue(KEY_PASSWORD)
    private var token: String by preferences.encryptedValue(KEY_TOKEN)
    private var session: String by preferences.encryptedValue(KEY_SESSION)

    override fun getCredentials(): Single<Credentials> {
        return Single.fromCallable {
            Credentials(
                login = login,
                password = password,
                token = Token(
                    tokenValue = token,
                    session = session
                )
            )
        }
    }

    override fun writeCredentials(credentials: Credentials): Completable {
        return Completable.fromAction {
            login = credentials.login
            password = credentials.password
            token = credentials.token.tokenValue
            session = credentials.token.session

            credentialsProvider.token = credentials.token
        }
    }

    private companion object {

        private const val KEY_TOKEN = "e0f7249e839828101c4a869a976a889d"
        private const val KEY_SESSION = "as40f7249e839828101c4a869a976a8892"
        private const val KEY_PASSWORD = "b3cfc9d67f6e912eb420cab319e4133c"
        private const val KEY_LOGIN = "933ea9b377e74fd81e2b68543afe60bb"

    }

}