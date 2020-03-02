package com.vmedia.core.network.mapper

import com.vmedia.core.common.obj.Currency
import com.vmedia.core.common.obj.Money
import com.vmedia.core.common.util.Mapper
import com.vmedia.core.network.api.entity.rest.TableValuesModel
import java.math.BigDecimal

internal abstract class TableValuesMapper<TO> : Mapper<TableValuesModel, List<TO>> {

    final override fun map(from: TableValuesModel): List<TO> {
        return from.dataRow.map(::mapItem)
    }

    protected abstract fun mapItem(tableValues: List<String>): TO

    protected fun String.toMoney(): Money? {
        if (isBlank()) return null

        return Money(
            currency = Currency.USD,
            value = BigDecimal(takeLast(length - 2))
        )
    }

}