package com.vmedia.core.common.pure.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

const val FORMAT_TABLEVALUES = "yyyy-MM-dd"
const val FORMAT_PACKAGEVERSION = "yyyy-MM-dd HH:mm:ss"
const val FORMAT_RSS = "EEE, d MMM yyyy HH:mm:ss Z"

const val FORMAT_DDMMYYYY = "dd.MM.yyyy"

val EMPTY_DATE = Date(0L)

val currentDate: Date
    get() = Calendar.getInstance().time

fun Date.format(format: String): String {
    return format.toDateFormat().format(this)
}

fun String.parse(format: String) : Date {
    return format.toDateFormat().parse(this)!!
}

private fun String.toDateFormat(locale: Locale = Locale.US) : DateFormat {
    return SimpleDateFormat(this, locale)
}