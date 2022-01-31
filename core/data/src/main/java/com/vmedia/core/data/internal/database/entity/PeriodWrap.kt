package com.vmedia.core.data.internal.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.vmedia.core.common.pure.obj.Period

@Entity(
    tableName = "Period",
    indices = [Index(value = ["year", "month"], unique = true)]
)
internal class PeriodWrap(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @Embedded val period: Period
)

internal fun Period.wrap(): PeriodWrap {
    return PeriodWrap(period = this)
}