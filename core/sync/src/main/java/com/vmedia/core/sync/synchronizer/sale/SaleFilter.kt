package com.vmedia.core.sync.synchronizer.sale

import com.vmedia.core.common.pure.obj.Period
import com.vmedia.core.common.pure.obj.endDate
import com.vmedia.core.common.pure.obj.startDate
import com.vmedia.core.common.pure.util.EMPTY_DATE
import com.vmedia.core.common.pure.util.ItemFilter
import com.vmedia.core.data.internal.database.entity.Sale
import com.vmedia.core.sync._SaleLastDateProvider
import com.vmedia.core.sync.synchronizer.SynchronizationPeriodsProvider

internal class SaleFilter(
    private val periodsProvider: SynchronizationPeriodsProvider,
    private val saleLastDateProvider: _SaleLastDateProvider
) : ItemFilter<Sale>() {

    override fun filter(item: Sale): Boolean {
        val salePeriod = periodsProvider.periods.find { item.isBelongToPeriod(it) }!!
        var lastSaleDate = saleLastDateProvider.invoke(salePeriod, item.assetId, item.priceUsd)

        if (lastSaleDate == null) {
            lastSaleDate = EMPTY_DATE
        }

        return item.date >= lastSaleDate
    }

    private companion object {

        private fun Sale.isBelongToPeriod(period: Period): Boolean {
            return date <= period.endDate && date >= period.startDate
        }

    }

}