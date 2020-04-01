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
)
data class Revenue(
    @PrimaryKey val periodId: Long,
    val date: Date,
    val valueUsd: BigDecimal,
    val isSale: Boolean
)