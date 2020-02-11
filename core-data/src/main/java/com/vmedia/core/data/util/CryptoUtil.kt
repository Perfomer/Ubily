package com.vmedia.core.data.util

import com.vmedia.core.data.BuildConfig
import org.jasypt.util.text.BasicTextEncryptor

val NO_VALUE_ENCRYPT by lazy { "".encrypt() }

private val encryptor by lazy {
    BasicTextEncryptor().apply { setPassword(BuildConfig.CRYPTO_SEED) }
}

fun String.encrypt() = encryptor.encrypt(this)!!
fun String.decrypt() = encryptor.decrypt(this)!!