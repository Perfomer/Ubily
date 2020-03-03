package com.vmedia.core.data.internal.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.*

@Entity
data class Revenue(
    @PrimaryKey(autoGenerate = true) val id : Long,
    val date: Date,
    val valueUsd: BigDecimal,
    val isSale: Boolean,
    val isFixing: Boolean
)