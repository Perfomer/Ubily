package com.vmedia.feature.assetdetails.api

import com.vmedia.core.navigation.ScreenDestination

class AssetDetailsScreen(assetId: Long) : ScreenDestination(
    BEAN_FRAGMENT_ASSETDETAILS,
    assetId
)