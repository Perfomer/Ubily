package com.vmedia.core.common.android.obj

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.vmedia.core.common.android.R
import com.vmedia.core.common.android.obj.EventType.*

enum class EventType {
    SALE,
    FREE_DOWNLOAD,
    REVIEW,
    ASSET,
    PAYOUT,
    REVENUE,
    ANNIVERSARY_SALE,
    INITIALIZATION
}

val EventType.titleResource: Int
    @StringRes
    get() = when (this) {
        SALE -> R.string.event_sale_title
        FREE_DOWNLOAD -> R.string.event_free_download_title
        REVIEW -> R.string.event_review_title
        ASSET -> R.string.event_asset_title
        PAYOUT -> R.string.event_payout_title
        REVENUE -> R.string.event_revenue_title
        INITIALIZATION -> R.string.event_initialized_title
        else -> TODO()
    }

val EventType.descriptionResource: Int
    @StringRes
    get() = when (this) {
        SALE -> R.string.event_sale_text
        FREE_DOWNLOAD -> R.string.event_free_download_text
        REVIEW -> R.string.event_review_text
        ASSET -> R.string.event_asset_text
        PAYOUT -> R.string.event_payout_text
        REVENUE -> R.string.event_revenue_text
        INITIALIZATION -> R.string.event_initialized_text
        else -> TODO()
    }

val EventType.iconResource: Int
    @DrawableRes get() = when (this) {
        SALE, FREE_DOWNLOAD -> R.drawable.ic_event_sale
        REVIEW -> R.drawable.ic_event_review
        PAYOUT, REVENUE -> R.drawable.ic_event_revenue
        INITIALIZATION -> R.drawable.ic_star
        ASSET -> R.drawable.ic_event_asset
        else -> R.drawable.logo_ubily
    }