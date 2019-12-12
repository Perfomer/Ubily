package com.vmedia.feature.auth.presentation

import androidx.annotation.StringRes
import com.vmedia.feature.auth.R

internal enum class AuthInputError(@StringRes val textResource: Int) {

    INCORRECT_LOGIN(R.string.auth_error_incorrect_login),
    INCORRECT_PASSWORD(R.string.auth_error_incorrect_password)

}