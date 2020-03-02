package com.vmedia.core.common.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

const val FORMAT_ddMMyy = "dd-MM-yy"
const val FORMAT_yyyyMMdd = "yyyy-MM-dd"

val currentDate: Date
    get() = Calendar.getInstance().time

fun Date.format(format: String): String {
    return format.toDateFormat().format(this)
}

fun String.parse(format: String) : Date {
    return format.toDateFormat().parse(this)!!
}

private fun String.toDateFormat(locale: Locale = Locale.getDefault()) : DateFormat {
    return SimpleDateFormat(this, locale)
}