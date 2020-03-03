package com.vmedia.core.data.internal.database.entity

import androidx.annotation.IntRange
import androidx.room.*
import com.vmedia.core.data.KeyEntity
import java.util.*

@Entity(
    indices = [Index(value = ["authorId", "assetId"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = Asset::class,
            childColumns = ["assetId"],
            parentColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            childColumns = ["authorId"],
            parentColumns = ["id"],
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
    @Embedded(prefix = "reply_") val publisherReply: PublisherReply?
) : KeyEntity<Long>

data class PublisherReply(
    val comment: String?,
    val publishingDate: Date?
)