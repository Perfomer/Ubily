package com.vmedia.core.common.obj.creds

data class Token(
    val tokenValue: String = "",
    val session: String = ""
) {

    val isEmpty: Boolean by lazy {
        tokenValue.isBlank() || session.isBlank()
    }

}