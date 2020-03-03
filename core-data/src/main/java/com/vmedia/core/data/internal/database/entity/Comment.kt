package com.vmedia.core.data.internal.database.entity

import androidx.annotation.IntRange
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.vmedia.core.data.KeyEntity
import java.util.*

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Asset::class,
            childColumns = arrayOf("assetId"),
            parentColumns = arrayOf("id"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            childColumns = arrayOf("authorId"),
            parentColumns = arrayOf("id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Comment(
    @PrimaryKey(autoGenerate = true) override val id: Long,
    val assetId: Long,
    val authorId: Long,
    @IntRange(from = 1, to = 5) val rating: Int,
    val title: String,
    val comment: String,
    val publishingDate: Date,
    @Embedded(prefix = "publisherreply_") val publisherReply: PublisherReply?
) : KeyEntity<Long>

data class PublisherReply(
    val comment: String?,
    val publishingDate: Date?
)