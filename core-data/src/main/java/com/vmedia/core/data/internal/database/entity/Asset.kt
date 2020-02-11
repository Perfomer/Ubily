package com.vmedia.core.data.internal.database.entity

import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vmedia.core.data.KeyEntity
import com.vmedia.core.data.R
import com.vmedia.core.data.internal.database.entity.AssetStatus.*
import java.util.*

@Entity
data class Asset(
    @PrimaryKey override val id: Long,
    val creationDate: Date,
    val modificationDate: Date,
    val publishingDate: Date,
    val categoryId: Long,
    val versionId: Long,
    val price: Double,
    val totalFileSize: Double,
    val name: String,
    val description: String,
    val version: String,
    val shortUrl: String,
    val bigImage: String,
    val smallImage: String,
    val iconImage: String,
    val status: AssetStatus
) : KeyEntity<Long>

enum class AssetStatus {
    PUBLISHED,
    DISABLED,
    DEPRECATED,
    DRAFT,
    PENDING_REVIEW,
    DECLINED,
}

val AssetStatus.stringResource: Int
    @StringRes get() = when (this) {
        PUBLISHED -> R.string.asset_status_published
        DISABLED -> R.string.asset_status_disabled
        DEPRECATED -> R.string.asset_status_deprecated
        DRAFT -> R.string.asset_status_draft
        PENDING_REVIEW -> R.string.asset_status_pending_review
        DECLINED -> R.string.asset_status_declined
    }

val AssetStatus.value: String
    get() = when (this) {
        PUBLISHED -> "published"
        DISABLED -> "disabled"
        DEPRECATED -> "deprecated"
        DRAFT -> "draft"
        PENDING_REVIEW -> "pendingReview"
        DECLINED -> "declined"
    }