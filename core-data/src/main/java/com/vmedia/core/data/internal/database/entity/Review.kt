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
data class Review(
    @PrimaryKey(autoGenerate = true) override val id: Long = 0L,
    val assetId: Long,
    val authorId: Long,
    val title: String,
    @IntRange(from = 1, to = 5) val rating: Int,
    @Embedded val comment: CommentBody,
    @Embedded(prefix = "reply_") val publisherReply: CommentBody?
) : KeyEntity<Long>

data class CommentBody(
    val comment: String,
    val publishingDate: Date
)