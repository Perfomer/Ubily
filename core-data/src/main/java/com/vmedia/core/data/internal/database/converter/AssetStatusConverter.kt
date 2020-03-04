package com.vmedia.core.data.internal.database.converter

import androidx.room.TypeConverter
import com.vmedia.core.common.obj.AssetStatus

class AssetStatusConverter : StringConverter<AssetStatus> {

    @TypeConverter
    override fun from(source: String) = AssetStatus.valueOf(source)

    @TypeConverter
    override fun to(item: AssetStatus) = item.name

}