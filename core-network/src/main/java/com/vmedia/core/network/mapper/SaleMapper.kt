package com.vmedia.core.network.mapper

import com.vmedia.core.common.util.FORMAT_TABLEVALUES
import com.vmedia.core.common.util.parse
import com.vmedia.core.network.api.entity.SaleDto

internal object SaleMapper : TableValuesMapper<SaleDto>() {

    override fun mapItem(tableValues: List<String>): SaleDto {
        return SaleDto(
            assetName = tableValues[0],
            price = tableValues[1].toMoney()!!,
            salesQuantity = tableValues[2].toInt(),
            refundsQuantity = tableValues[3].toInt(),
            chargebacksQuantity = tableValues[4].toInt(),
            firstSale = tableValues[6].parse(FORMAT_TABLEVALUES),
            lastSale = tableValues[7].parse(FORMAT_TABLEVALUES)
        )
    }

}