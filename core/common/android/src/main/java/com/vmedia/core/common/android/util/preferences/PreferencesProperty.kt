package com.vmedia.core.common.android.util.preferences

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.vmedia.core.common.android.util.preferences.PreferencesWriteStrategy.COMMIT
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

inline fun <reified T : Any> SharedPreferences.value(
    key: String,
    defaultValue: T,
    writeStrategy: PreferencesWriteStrategy = COMMIT
): ReadWriteProperty<Any, T> {
    return PreferencesProperty(
        sharedPreferences = this,
        clazz = T::class,
        key = key,
        defaultValue = defaultValue,
        writeStrategy = writeStrategy
    )
}

@Suppress("UNCHECKED_CAST")
open class PreferencesProperty<T : Any>(
    private val sharedPreferences: SharedPreferences,
    private val clazz: KClass<T>,
    private val key: String,
    private val defaultValue: T,
    private val writeStrategy: PreferencesWriteStrategy
) : ReadWriteProperty<Any, T> {

    @Suppress("IMPLICIT_CAST_TO_ANY")
    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return when (clazz) {
            String::class -> sharedPreferences.getString(key, defaultValue as String)
            Boolean::class -> sharedPreferences.getBoolean(key, defaultValue as Boolean)
            Int::class -> sharedPreferences.getInt(key, defaultValue as Int)
            Float::class -> sharedPreferences.getFloat(key, defaultValue as Float)
            Long::class -> sharedPreferences.getLong(key, defaultValue as Long)
            Set::class -> sharedPreferences.getStringSet(key, defaultValue as Set<String>)
            else -> throw IllegalStateException("T generic param is unsupported: ${clazz.simpleName}")
        } as T
    }

    @SuppressLint("CommitPrefEdits")
    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        val editor = sharedPreferences.edit()

        when (clazz) {
            String::class -> editor.putString(key, value as String)
            Boolean::class -> editor.putBoolean(key, value as Boolean)
            Int::class -> editor.putInt(key, value as Int)
            Float::class -> editor.putFloat(key, value as Float)
            Long::class -> editor.putLong(key, value as Long)
            Set::class -> editor.putStringSet(key, value as Set<String>)
            else -> throw IllegalStateException("T generic param is unsupported: ${clazz.simpleName}")
        }

        editor.write(writeStrategy)
    }

}