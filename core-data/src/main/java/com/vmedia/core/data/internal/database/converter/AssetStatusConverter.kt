package com.vmedia.core.data.internal.database.converter

import androidx.room.TypeConverter
import com.vmedia.core.data.internal.database.entity.AssetStatus

@Suppress("unused")
class AssetStatusConverter {

    @TypeConverter
    fun fromAssetStatus(assetStatus: AssetStatus) = assetStatus.name

    @TypeConverter
    fun fromString(status: String) = AssetStatus.valueOf(status)

}