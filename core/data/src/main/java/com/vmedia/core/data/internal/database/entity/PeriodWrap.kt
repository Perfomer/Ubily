package com.vmedia.core.data.internal.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.vmedia.core.common.android.obj.Period
import com.vmedia.core.data.KeyEntity

@Entity(
    tableName = "Period",
    indices = [Index(value = ["year", "month"], unique = true)]
)
internal class PeriodWrap(
    @PrimaryKey(autoGenerate = true) override val id: Long = 0L,
    @Embedded val period: Period
): KeyEntity<Long>

internal fun Period.wrap(): PeriodWrap {
    return PeriodWrap(period = this)
}