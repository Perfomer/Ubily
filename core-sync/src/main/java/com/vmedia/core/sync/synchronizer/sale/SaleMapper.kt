package com.vmedia.core.sync.synchronizer.sale

import com.vmedia.core.common.util.Mapper
import com.vmedia.core.data.internal.database.entity.Sale
import com.vmedia.core.network.entity.SaleDto
import com.vmedia.core.sync._AssetProviderByName

internal class SaleMapper(
    private val assetProvider: _AssetProviderByName
): Mapper<SaleDto, Sale> {

    override fun map(from: SaleDto): Sale {
        return Sale(
            assetId = assetProvider.invoke(from.assetName).id,
            priceUsd = from.price.value,
            date = from.lastSale,
            quantity = from.salesQuantity
        )
    }

}