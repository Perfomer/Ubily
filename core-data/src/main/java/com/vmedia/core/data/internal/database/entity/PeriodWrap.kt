package com.vmedia.core.data.internal.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.vmedia.core.common.obj.Period

@Entity(
    tableName = "Period",
    indices = [Index(value = ["year", "month"], unique = true)]
)
class PeriodWrap(
    @PrimaryKey val id: Long,
    @Embedded val period: Period
)