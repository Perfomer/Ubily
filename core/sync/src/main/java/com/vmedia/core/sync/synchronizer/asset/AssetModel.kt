package com.vmedia.core.sync.synchronizer.asset

import com.vmedia.core.data.internal.database.entity.Artwork
import com.vmedia.core.data.internal.database.entity.Asset

class AssetModel(
    val asset: Asset,
    val artworks: List<Artwork>,
    val keywords: Set<String>
)