package com.vmedia.core.network.mapper

import com.vmedia.core.common.util.FORMAT_TABLEVALUES
import com.vmedia.core.common.util.parse
import com.vmedia.core.network.api.entity.AdditionalTableValuesModel
import com.vmedia.core.network.entity.SaleDto

internal object SaleMapper : TableValuesMapper<SaleDto>() {

    override fun mapItem(
        dataRow: List<String>,
        extraRow: AdditionalTableValuesModel
    ): SaleDto {
        return SaleDto(
            assetName = dataRow[0],
            assetUrl = extraRow.shortUrl,
            price = dataRow[1].toMoney()!!,
            salesQuantity = dataRow[2].toInt(),
            refundsQuantity = dataRow[3].toInt(),
            chargebacksQuantity = dataRow[4].toInt(),
            firstSale = dataRow[6].parse(FORMAT_TABLEVALUES),
            lastSale = dataRow[7].parse(FORMAT_TABLEVALUES)
        )
    }

}