package com.vmedia.core.data.internal.database.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.vmedia.core.common.android.util.EMPTY_DATE
import com.vmedia.core.common.android.util.times
import com.vmedia.core.data.KeyEntity
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
    @PrimaryKey(autoGenerate = true) override val id: Long = 0L,
    val assetId: Long = 0L,
    val priceUsd: BigDecimal = BigDecimal.ZERO,
    val date: Date = EMPTY_DATE,
    val quantity: Int = 0
) : KeyEntity<Long> {

    @Ignore
    val amount: BigDecimal = priceUsd * quantity

    @Ignore
    val isFree: Boolean = priceUsd == BigDecimal.ZERO

}