package com.vmedia.core.data.internal.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["assetId", "keywordId"],
    foreignKeys = [
        ForeignKey(
            entity = Asset::class,
            parentColumns = ["id"],
            childColumns = ["assetId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Keyword::class,
            parentColumns = ["id"],
            childColumns = ["keywordId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class AssetKeyword(
    val assetId: Long,
    val keywordId: Long
)