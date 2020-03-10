package com.vmedia.core.sync.synchronizer.asset

import com.vmedia.core.common.util.Filter
import com.vmedia.core.network.entity.AssetDto
import com.vmedia.core.sync._AssetProvider

internal class AssetFilter(
    private val assetProvider: _AssetProvider
) : Filter<AssetDto> {

    override fun filter(source: List<AssetDto>): List<AssetDto> {
        return source.filter { it.isDateUpdated() || it.isStatusUpdated() }
    }

    private fun AssetDto.isDateUpdated(): Boolean {
        return assetProvider.invoke(id).modificationDate != modificationDate
    }

    private fun AssetDto.isStatusUpdated(): Boolean {
        return assetProvider.invoke(id).status != status
    }

}