package com.vmedia.core.data.internal.database.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.vmedia.core.data.KeyEntity
import java.util.*

@Entity(
    indices = [Index(value = ["assetId", "price", "date"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = Asset::class,
        parentColumns = ["id"],
        childColumns = ["assetId"],
        onDelete = CASCADE
    )]
)
data class Sale(
    @PrimaryKey(autoGenerate = true) override val id: Long,
    val assetId: Long,
    val price: Double,
    val date: Date,
    val quantity: Int
) : KeyEntity<Long> {

    @Ignore
    val amount: Double = price * quantity

}