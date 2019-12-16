package com.vmedia.core.data.datasource

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.vmedia.core.data.internal.Credentials
import com.vmedia.core.data.util.decrypt
import com.vmedia.core.data.util.encrypt
import io.reactivex.Completable
import io.reactivex.Single

@SuppressLint("ApplySharedPref")
class PreferencesDataSource(
    private val preferences: SharedPreferences
) {

    fun getCredentials(): Single<Credentials> {
        fun read(key: String) = preferences.getString(key, NO_VALUE)!!.decrypt()

        return Single.fromCallable {
            Credentials(
                login = read(KEY_LOGIN),
                password = read(KEY_PASSWORD),
                token = read(KEY_TOKEN)
            )
        }
    }

    fun writeCredentials(credentials: Credentials): Completable {
        return Completable.fromAction {
            preferences.edit()
                .putString(KEY_LOGIN, credentials.login.encrypt())
                .putString(KEY_PASSWORD, credentials.password.encrypt())
                .putString(KEY_TOKEN, credentials.token.encrypt())
                .commit()
        }
    }

    private companion object {

        private const val KEY_TOKEN = "e0f7249e839828101c4a869a976a889d"
        private const val KEY_PASSWORD = "b3cfc9d67f6e912eb420cab319e4133c"
        private const val KEY_LOGIN = "933ea9b377e74fd81e2b68543afe60bb"

        private const val NO_VALUE = "OuJxVVbXMeTQzymMGnAn5w=="
    }

}