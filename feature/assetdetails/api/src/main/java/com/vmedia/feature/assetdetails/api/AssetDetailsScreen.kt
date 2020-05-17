package com.vmedia.feature.assetdetails.api

import com.vmedia.core.navigation.ScreenDestination.ScreenDestinationNew

class AssetDetailsScreen(assetId: Long) : ScreenDestinationNew(
    BEAN_FRAGMENT_ASSETDETAILS,
    assetId
)