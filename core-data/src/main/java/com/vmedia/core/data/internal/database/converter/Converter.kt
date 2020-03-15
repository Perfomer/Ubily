package com.vmedia.core.data.internal.database.converter

import androidx.room.TypeConverter

internal interface Converter<T, R> {

    fun from(source: R): T

    fun to(item: T): R

}

internal abstract class StringConverter<T> : Converter<T, String> {

    @TypeConverter
    override fun to(item: T) = item.toString()

}

/**
 * Enum Converter class
 * Implements [Converter] interface.
 *
 * Automatically converts enums to strings and strings to enums.
 * You have to extend the class only and pass Enum class argument.
 */
internal abstract class EnumConverter<T : Enum<T>>(
    private val clazz: Class<T>
) : Converter<T, String> {

    @TypeConverter
    final override fun from(source: String): T {
        return clazz.enumConstants!!.find { it.name == source }!!
    }

    @TypeConverter
    final override fun to(item: T): String {
        return item.name
    }

}