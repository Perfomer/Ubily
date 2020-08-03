package com.vmedia.core.common.android.util.preferences

import android.content.SharedPreferences

enum class PreferencesWriteStrategy {
    APPLY,
    COMMIT
}

fun SharedPreferences.Editor.write(strategy: PreferencesWriteStrategy) {
    when (strategy) {
        PreferencesWriteStrategy.APPLY -> apply()
        PreferencesWriteStrategy.COMMIT -> commit()
    }
}