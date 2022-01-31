package com.vmedia.core.data.internal.database.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.vmedia.core.common.pure.util.EMPTY_DATE
import com.vmedia.core.common.pure.util.times
import java.math.BigDecimal
import java.util.*

@Entity(
    indices = [Index(value = ["assetId", "priceUsd", "date"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = Asset::class,
        parentColumns = ["id"],
        childColumns = ["assetId"],
        onDelete = CASCADE
    )]
)
data class Sale(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val assetId: Long = 0L,
    val priceUsd: BigDecimal = BigDecimal.ZERO,
    val date: Date = EMPTY_DATE,
    val quantity: Int = 0
) {

    @Ignore
    val amount: BigDecimal = priceUsd * quantity

    @Ignore
    val isFree: Boolean = priceUsd == BigDecimal.ZERO

}