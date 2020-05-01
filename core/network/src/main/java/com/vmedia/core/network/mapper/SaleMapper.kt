package com.vmedia.core.network.mapper

import com.vmedia.core.common.android.util.FORMAT_TABLEVALUES
import com.vmedia.core.common.android.util.parse
import com.vmedia.core.network.api.entity.ExtraTableValues
import com.vmedia.core.network.entity.SaleDto

internal object SaleMapper : TableValuesMapper<SaleDto>() {

    override fun mapItem(
        dataRow: List<String>,
        extraRow: ExtraTableValues?
    ): SaleDto {
        return SaleDto(
            assetName = dataRow[0],
            assetUrl = extraRow!!.shortUrl,
            price = dataRow[1].toMoney()!!,
            salesQuantity = dataRow[2].toInt(),
            refundsQuantity = dataRow[3].toInt(),
            chargebacksQuantity = dataRow[4].toInt(),
            firstSale = dataRow[6].parse(FORMAT_TABLEVALUES),
            lastSale = dataRow[7].parse(FORMAT_TABLEVALUES)
        )
    }

}