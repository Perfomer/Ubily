package com.vmedia.core.data.internal.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.*

@Entity(
    foreignKeys = [ForeignKey(
        entity = PeriodWrap::class,
        parentColumns = ["id"],
        childColumns = ["periodId"]
    )]
)data class Payout(
    @PrimaryKey val periodId: Long,
    val valueUsd: BigDecimal,
    val date: Date,
    val autoPayout: Boolean,
    val paypal: Boolean,
    val isFailed: Boolean
)