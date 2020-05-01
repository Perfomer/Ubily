package com.vmedia.core.data.datasource.impl

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.vmedia.core.common.pure.obj.creds.Credentials
import com.vmedia.core.common.pure.obj.creds.Token
import com.vmedia.core.data.datasource.CredentialsDataSource
import com.vmedia.core.data.util.NO_VALUE_ENCRYPT
import com.vmedia.core.data.util.decrypt
import com.vmedia.core.data.util.encrypt
import com.vmedia.core.network.datasource.MutableNetworkCredentialsProvider
import io.reactivex.Completable
import io.reactivex.Single

@SuppressLint("ApplySharedPref")
internal class CredentialsDataSourceImpl(
    private val preferences: SharedPreferences,
    private val credentialsProvider: MutableNetworkCredentialsProvider
) : CredentialsDataSource {

    override fun getCredentials(): Single<Credentials> {
        fun read(key: String) = preferences.getString(key, NO_VALUE_ENCRYPT)!!.decrypt()

        return Single.fromCallable {
            Credentials(
                login = read(KEY_LOGIN),
                password = read(KEY_PASSWORD),
                token = Token(
                    tokenValue = read(KEY_TOKEN),
                    session = read(KEY_SESSION)
                )
            )
        }
    }

    override fun writeCredentials(credentials: Credentials): Completable {
        fun SharedPreferences.Editor.write(key: String, value: String): SharedPreferences.Editor {
            return putString(key, value.encrypt())
        }

        return Completable.fromAction {
            preferences.edit()
                .write(KEY_LOGIN, credentials.login)
                .write(KEY_PASSWORD, credentials.password)
                .write(KEY_TOKEN, credentials.token.tokenValue)
                .write(KEY_SESSION, credentials.token.session)
                .commit()

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