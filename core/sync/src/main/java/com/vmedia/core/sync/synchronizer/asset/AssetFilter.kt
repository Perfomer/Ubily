package com.vmedia.core.sync.synchronizer.asset

import com.vmedia.core.common.pure.util.ItemFilter
import com.vmedia.core.network.entity.AssetDto
import com.vmedia.core.sync._AssetProviderById

internal class AssetFilter(
    private val assetProvider: _AssetProviderById
) : ItemFilter<AssetDto>() {

    override fun filter(item: AssetDto): Boolean {
        return item.isDateUpdated() || item.isStatusUpdated()
    }

    private fun AssetDto.isDateUpdated(): Boolean {
        val oldAsset = assetProvider.invoke(id) ?: return true
        return oldAsset.modificationDate != modificationDate
    }

    private fun AssetDto.isStatusUpdated(): Boolean {
        val oldAsset = assetProvider.invoke(id) ?: return true
        return oldAsset.status != status
    }

}