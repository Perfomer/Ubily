package com.vmedia.core.data.internal.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.vmedia.core.data.KeyEntity

@Entity(
    indices = [
        Index(value = ["previewUrl"], unique = true),
        Index(value = ["assetId"])
    ],
    foreignKeys = [ForeignKey(
        entity = Asset::class,
        parentColumns = ["id"],
        childColumns = ["assetId"],
        onDelete = CASCADE
    )]
)
data class Artwork(
    @PrimaryKey(autoGenerate = true) override val id: Long = 0L,
    val assetId: Long,
    val mediaType: MediaType,
    val previewUrl: String,
    val contentUrl: String?
) : KeyEntity<Long>

enum class MediaType {
    IMAGE,
    VIDEO
}