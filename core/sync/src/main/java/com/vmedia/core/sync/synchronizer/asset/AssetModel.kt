package com.vmedia.core.sync.synchronizer.asset

import com.vmedia.core.data.internal.database.entity.Asset
import com.vmedia.core.data.internal.database.entity.AssetImage

class AssetModel(
    val asset: Asset,
    val images: List<AssetImage>,
    val keywords: Set<String>
)