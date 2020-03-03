package com.vmedia.core.common.obj.creds

data class Credentials(
    val login: String,
    val password: String,
    val token: Token
)