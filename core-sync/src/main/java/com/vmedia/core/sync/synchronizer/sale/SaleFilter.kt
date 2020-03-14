package com.vmedia.core.sync.synchronizer.sale

import com.vmedia.core.common.obj.Period
import com.vmedia.core.common.obj.endDate
import com.vmedia.core.common.obj.startDate
import com.vmedia.core.common.util.ItemFilter
import com.vmedia.core.data.internal.database.entity.Sale
import com.vmedia.core.sync._LastSaleDateProvider
import com.vmedia.core.sync.synchronizer.SynchronizationPeriodsProvider

internal class SaleFilter(
    private val periodsProvider: SynchronizationPeriodsProvider,
    private val lastSaleDateProvider: _LastSaleDateProvider
) : ItemFilter<Sale>() {

    override fun filter(item: Sale): Boolean {
        val salePeriod = periodsProvider.periods.find { item.isBelongToPeriod(it) }!!
        val lastSaleDate = lastSaleDateProvider.invoke(salePeriod, item.assetId, item.priceUsd)

        return item.date >= lastSaleDate
    }

    private companion object {

        fun Sale.isBelongToPeriod(period: Period): Boolean {
            return date <= period.endDate && date >= period.startDate
        }

    }

}