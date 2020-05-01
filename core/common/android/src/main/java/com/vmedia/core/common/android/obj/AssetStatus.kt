package com.vmedia.core.common.android.obj

import androidx.annotation.StringRes
import com.vmedia.core.common.android.R
import com.vmedia.core.common.android.obj.AssetStatus.*

enum class AssetStatus {
    PUBLISHED,
    DISABLED,
    DEPRECATED,
    DRAFT,
    PENDING_REVIEW,
    DECLINED,
    NONE,
}

fun String.toAssetStatus(): AssetStatus {
    return when (this) {
        STATUS_PUBLISHED -> PUBLISHED
        STATUS_DISABLED -> DISABLED
        STATUS_DEPRECATED -> DEPRECATED
        STATUS_DRAFT -> DRAFT
        STATUS_PENDING_REVIEW -> PENDING_REVIEW
        STATUS_DECLINED -> DECLINED
        else -> NONE
    }
}

val AssetStatus.labelResource: Int
    @StringRes get() = when (this) {
        PUBLISHED -> R.string.asset_status_published
        DISABLED -> R.string.asset_status_disabled
        DEPRECATED -> R.string.asset_status_deprecated
        DRAFT -> R.string.asset_status_draft
        PENDING_REVIEW -> R.string.asset_status_pending_review
        DECLINED -> R.string.asset_status_declined
        NONE -> R.string.asset_status_none
    }

val AssetStatus.value: String
    get() = when (this) {
        PUBLISHED -> STATUS_PUBLISHED
        DISABLED -> STATUS_DISABLED
        DEPRECATED -> STATUS_DEPRECATED
        DRAFT -> STATUS_DRAFT
        PENDING_REVIEW -> STATUS_PENDING_REVIEW
        DECLINED -> STATUS_DECLINED
        NONE -> ""
    }

private const val STATUS_PUBLISHED = "published"
private const val STATUS_DISABLED = "disabled"
private const val STATUS_DEPRECATED = "deprecated"
private const val STATUS_DRAFT = "draft"
private const val STATUS_PENDING_REVIEW = "pendingReview"
private const val STATUS_DECLINED = "declined"