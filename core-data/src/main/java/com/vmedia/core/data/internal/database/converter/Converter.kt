package com.vmedia.core.data.internal.database.converter

interface Converter<T, R> {

    fun from(source: R): T

    fun to(item: T): R

}

interface StringConverter<T> : Converter<T, String>