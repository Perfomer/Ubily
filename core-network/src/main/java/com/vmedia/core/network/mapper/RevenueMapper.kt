package com.vmedia.core.network.mapper

import com.vmedia.core.common.util.FORMAT_TABLEVALUES
import com.vmedia.core.common.util.parse
import com.vmedia.core.network.api.entity.RevenueDto

internal object RevenueMapper : TableValuesMapper<RevenueDto>() {

    override fun mapItem(tableValues: List<String>): RevenueDto {
        return RevenueDto(
            date = tableValues[0].parse(FORMAT_TABLEVALUES),
            description = tableValues[1],
            debit = tableValues[2].toMoney(),
            credit = tableValues[3].toMoney(),
            balance = tableValues[4].toMoney()
        )
    }

}