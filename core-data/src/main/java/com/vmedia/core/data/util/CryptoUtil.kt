package com.vmedia.core.data.util

import org.jasypt.util.text.BasicTextEncryptor

private const val SEED = "99f063bce46eda3745af899dbae20542"

private val encryptor = BasicTextEncryptor().apply { setPassword(SEED) }

fun String.encrypt() = encryptor.encrypt(this)
fun String.decrypt() = encryptor.decrypt(this)