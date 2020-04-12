package com.vmedia.feature.sync.presentation.model

import androidx.annotation.StringRes
import com.vmedia.feature.sync.presentation.R

internal enum class SyncDataType(
    @StringRes val labelResource: Int
) {
    PUBLISHER(R.string.sync_data_publisher),
    ASSETS(R.string.sync_data_assets),
    REVIEWS(R.string.sync_data_reviews),
    SALES(R.string.sync_data_sales),
    DOWNLOADS(R.string.sync_data_downloads),
    REVENUE(R.string.sync_data_revenue)
}