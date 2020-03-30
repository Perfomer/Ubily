package com.vmedia.core.data.internal.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["url"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = Asset::class,
        parentColumns = ["id"],
        childColumns = ["assetId"],
        onDelete = CASCADE
    )]
)
data class AssetImage(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val assetId: Long,
    val url: String
)