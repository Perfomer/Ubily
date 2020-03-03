package com.vmedia.core.data.internal.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.*

@Entity
data class Payout(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val valueUsd: BigDecimal,
    val date: Date,
    val autoPayout: Boolean,
    val paypal: Boolean,
    val isFailed: Boolean
)