package com.vmedia.core.common.android.obj.creds

data class Token(
    val tokenValue: String = "",
    val session: String = ""
) {

    val isEmpty: Boolean by lazy {
        tokenValue.isBlank() || session.isBlank()
    }

}