package com.vmedia.core.data.internal.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.vmedia.core.common.android.util.EMPTY_DATE
import java.math.BigDecimal
import java.util.*

@Entity(
    foreignKeys = [ForeignKey(
        entity = PeriodWrap::class,
        parentColumns = ["id"],
        childColumns = ["periodId"]
    )]
)
data class Revenue(
    @PrimaryKey val periodId: Long,
    val date: Date = EMPTY_DATE,
    val valueUsd: BigDecimal = BigDecimal.ZERO,
    val isSale: Boolean = false
)