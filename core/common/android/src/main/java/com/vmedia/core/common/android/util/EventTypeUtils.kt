package com.vmedia.core.common.android.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.vmedia.core.common.android.R
import com.vmedia.core.common.pure.obj.EventType

val EventType.titleResource: Int
    @StringRes
    get() = when (this) {
        EventType.SALE -> R.string.event_sale_title
        EventType.FREE_DOWNLOAD -> R.string.event_free_download_title
        EventType.REVIEW -> R.string.event_review_title
        EventType.ASSET -> R.string.event_asset_title
        EventType.PAYOUT -> R.string.event_payout_title
        EventType.REVENUE -> R.string.event_revenue_title
        EventType.INITIALIZATION -> R.string.event_initialized_title
        else -> TODO()
    }

val EventType.descriptionResource: Int
    @StringRes
    get() = when (this) {
        EventType.SALE -> R.string.event_sale_text
        EventType.FREE_DOWNLOAD -> R.string.event_free_download_text
        EventType.REVIEW -> R.string.event_review_text
        EventType.ASSET -> R.string.event_asset_text
        EventType.PAYOUT -> R.string.event_payout_text
        EventType.REVENUE -> R.string.event_revenue_text
        EventType.INITIALIZATION -> R.string.event_initialized_text
        else -> TODO()
    }

val EventType.iconResource: Int
    @DrawableRes get() = when (this) {
        EventType.SALE, EventType.FREE_DOWNLOAD -> R.drawable.ic_event_sale
        EventType.REVIEW -> R.drawable.ic_event_review
        EventType.PAYOUT, EventType.REVENUE -> R.drawable.ic_event_revenue
        EventType.INITIALIZATION -> R.drawable.ic_star
        EventType.ASSET -> R.drawable.ic_event_asset
        else -> R.drawable.logo_ubily
    }