package com.vmedia.core.data.datasource.impl

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.vmedia.core.data.datasource.SettingsDataSource

@SuppressLint("ApplySharedPref")
internal class SettingsDataSourceImpl(
    private val preferences: SharedPreferences
): SettingsDataSource {



}