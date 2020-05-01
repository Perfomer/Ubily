package com.vmedia.core.common.pure.obj

import com.vmedia.core.common.pure.obj.AssetStatus.*

enum class AssetStatus {
    PUBLISHED,
    DISABLED,
    DEPRECATED,
    DRAFT,
    PENDING_REVIEW,
    DECLINED,
    NONE,
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

private const val STATUS_PUBLISHED = "published"
private const val STATUS_DISABLED = "disabled"
private const val STATUS_DEPRECATED = "deprecated"
private const val STATUS_DRAFT = "draft"
private const val STATUS_PENDING_REVIEW = "pendingReview"
private const val STATUS_DECLINED = "declined"