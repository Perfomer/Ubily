package com.vmedia.core.network.mapper

import com.vmedia.core.common.pure.obj.Money
import com.vmedia.core.common.pure.obj.toCurrency
import com.vmedia.core.common.pure.util.Mapper
import com.vmedia.core.common.pure.util.zipWithNullable
import com.vmedia.core.network.api.entity.ExtraTableValues
import com.vmedia.core.network.api.entity.TableValuesModel
import java.math.BigDecimal

internal abstract class TableValuesMapper<TO> : Mapper<TableValuesModel, List<TO>> {

    final override fun map(from: TableValuesModel): List<TO> {
        return from.dataRows.zipWithNullable(from.extraRows)
            .map { (dataRow, extraRow) -> mapItem(dataRow, extraRow) }
    }

    protected abstract fun mapItem(
        dataRow: List<String>,
        extraRow: ExtraTableValues?
    ): TO

    protected fun String.toMoney(): Money? {
        if (isBlank()) return null

        return Money(
            currency = get(0).toCurrency(),
            value = BigDecimal(takeLast(length - 2))
        )
    }

}