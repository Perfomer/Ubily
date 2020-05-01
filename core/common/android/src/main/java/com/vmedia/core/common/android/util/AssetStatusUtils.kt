package com.vmedia.core.common.android.util

import androidx.annotation.StringRes
import com.vmedia.core.common.android.R
import com.vmedia.core.common.pure.obj.AssetStatus

val AssetStatus.labelResource: Int
    @StringRes get() = when (this) {
        AssetStatus.PUBLISHED -> R.string.asset_status_published
        AssetStatus.DISABLED -> R.string.asset_status_disabled
        AssetStatus.DEPRECATED -> R.string.asset_status_deprecated
        AssetStatus.DRAFT -> R.string.asset_status_draft
        AssetStatus.PENDING_REVIEW -> R.string.asset_status_pending_review
        AssetStatus.DECLINED -> R.string.asset_status_declined
        AssetStatus.NONE -> R.string.asset_status_none
    }