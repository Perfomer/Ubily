package com.vmedia.core.data.internal.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.vmedia.core.common.util.EMPTY_DATE
import java.math.BigDecimal
import java.util.*

@Entity(
    foreignKeys = [ForeignKey(
        entity = PeriodWrap::class,
        parentColumns = ["id"],
        childColumns = ["periodId"]
    )]
)
data class Payout(
    @PrimaryKey val periodId: Long,
    val valueUsd: BigDecimal = BigDecimal.ZERO,
    val date: Date = EMPTY_DATE,
    val autoPayout: Boolean = false,
    val paypal: Boolean = false,
    val isFailed: Boolean = false
)