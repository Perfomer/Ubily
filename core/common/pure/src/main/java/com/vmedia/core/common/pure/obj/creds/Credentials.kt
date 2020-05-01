package com.vmedia.core.common.pure.obj.creds

data class Credentials(
    val login: String,
    val password: String,
    val token: Token
) {

    val isEmpty: Boolean by lazy {
        login.isBlank() || password.isBlank() || token.isEmpty
    }

}