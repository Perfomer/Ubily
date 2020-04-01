package com.vmedia.core.network.mapper

import com.vmedia.core.common.obj.Money
import com.vmedia.core.common.obj.toCurrency
import com.vmedia.core.common.util.Mapper
import com.vmedia.core.network.api.entity.AdditionalTableValuesModel
import com.vmedia.core.network.api.entity.TableValuesModel
import java.math.BigDecimal

internal abstract class TableValuesMapper<TO> : Mapper<TableValuesModel, List<TO>> {

    final override fun map(from: TableValuesModel): List<TO> {
        return from.dataRows
            .zip(from.extraRows)
            .map { (dataRow, extraRow) -> mapItem(dataRow, extraRow) }
    }

    protected abstract fun mapItem(
        dataRow: List<String>,
        extraRow: AdditionalTableValuesModel
    ): TO

    protected fun String.toMoney(): Money? {
        if (isBlank()) return null

        return Money(
            currency = get(0).toCurrency(),
            value = BigDecimal(takeLast(length - 2))
        )
    }

}