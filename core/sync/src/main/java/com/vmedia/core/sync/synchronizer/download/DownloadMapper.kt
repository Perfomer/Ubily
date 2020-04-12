package com.vmedia.core.sync.synchronizer.download

import com.vmedia.core.common.util.Mapper
import com.vmedia.core.data.internal.database.entity.Sale
import com.vmedia.core.network.entity.DownloadDto
import com.vmedia.core.sync._AssetProviderByUrl
import java.math.BigDecimal

internal class DownloadMapper(
    private val assetProvider: _AssetProviderByUrl
) : Mapper<DownloadDto, Sale> {

    override fun map(from: DownloadDto): Sale {
        return Sale(
            assetId = assetProvider.invoke(from.assetUrl).id,
            priceUsd = BigDecimal.ZERO,
            date = from.lastDownload,
            quantity = from.downloadsQuantity
        )
    }

}