package com.vmedia.core.data.datasource

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.vmedia.core.data.internal.Credentials
import com.vmedia.core.data.util.NO_VALUE_ENCRYPT
import com.vmedia.core.data.util.decrypt
import com.vmedia.core.data.util.encrypt
import io.reactivex.Completable
import io.reactivex.Single

@SuppressLint("ApplySharedPref")
class CredentialsDataSource(
    private val preferences: SharedPreferences
) {

    fun getCredentials(): Single<Credentials> {
        fun read(key: String) = preferences.getString(key, NO_VALUE_ENCRYPT)!!.decrypt()

        return Single.fromCallable {
            Credentials(
                login = read(KEY_LOGIN),
                password = read(KEY_PASSWORD),
                token = read(KEY_TOKEN)
            )
        }
    }

    fun writeCredentials(credentials: Credentials): Completable {
        fun Editor.putEncryptedString(key: String, value: String) = putString(key, value.encrypt())

        return Completable.fromAction {
            preferences.edit()
                .putEncryptedString(KEY_LOGIN, credentials.login)
                .putEncryptedString(KEY_PASSWORD, credentials.password)
                .putEncryptedString(KEY_TOKEN, credentials.token)
                .commit()
        }
    }

    private companion object {

        private const val KEY_TOKEN = "e0f7249e839828101c4a869a976a889d"
        private const val KEY_PASSWORD = "b3cfc9d67f6e912eb420cab319e4133c"
        private const val KEY_LOGIN = "933ea9b377e74fd81e2b68543afe60bb"

    }

}