package com.vmedia.core.common.android.util.preferences

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.vmedia.core.common.android.util.NO_VALUE_ENCRYPT
import com.vmedia.core.common.android.util.decrypt
import com.vmedia.core.common.android.util.encrypt
import com.vmedia.core.common.android.util.preferences.PreferencesWriteStrategy.COMMIT
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

inline fun <reified T : Any> SharedPreferences.encryptedValue(
    key: String,
    defaultValue: T,
    writeStrategy: PreferencesWriteStrategy = COMMIT
): ReadWriteProperty<Any, T> {
    return EncryptedPreferencesProperty(
        sharedPreferences = this,
        clazz = T::class,
        key = key,
        defaultValue = defaultValue,
        writeStrategy = writeStrategy
    )
}

@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
class EncryptedPreferencesProperty<T : Any>(
    private val sharedPreferences: SharedPreferences,
    private val clazz: KClass<T>,
    private val key: String,
    private val defaultValue: T,
    private val writeStrategy: PreferencesWriteStrategy
) : ReadWriteProperty<Any, T> {

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        val encryptedStringValue = sharedPreferences.getString(key, NO_VALUE_ENCRYPT)!!
        val decryptedStringValue = encryptedStringValue.decrypt()

        if (decryptedStringValue == NO_VALUE_ENCRYPT) return defaultValue

        return when (clazz) {
            String::class -> decryptedStringValue
            Boolean::class -> decryptedStringValue.toBoolean()
            Int::class -> decryptedStringValue.toInt()
            Float::class -> decryptedStringValue.toFloat()
            Long::class -> decryptedStringValue.toLong()
            Set::class -> decryptedStringValue.split(SET_SEPARATOR).toSet()
            else -> throw IllegalStateException("T generic param is unsupported: ${clazz.simpleName}")
        } as T
    }

    @SuppressLint("CommitPrefEdits")
    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        val editor = sharedPreferences.edit()

        val serializedValue = when (clazz) {
            String::class, Boolean::class, Int::class, Float::class, Long::class -> {
                value.toString()
            }

            Set::class -> {
                val set = value as Set<String>
                set.joinToString(separator = SET_SEPARATOR)
            }

            else -> {
                throw IllegalStateException("T generic param is unsupported: ${clazz.simpleName}")
            }
        }

        editor.putString(key, serializedValue.encrypt())
        editor.write(writeStrategy)
    }

    private companion object {

        private const val SET_SEPARATOR = ","

    }

}