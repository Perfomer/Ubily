package com.vmedia.core.sync.synchronizer.revenue

import com.vmedia.core.common.util.ItemFilter
import com.vmedia.core.network.entity.RevenueEventDto

internal object RevenueDtoFilter: ItemFilter<RevenueEventDto>() {

    override fun filter(item: RevenueEventDto): Boolean {
        return item is RevenueEventDto.Revenue || item is RevenueEventDto.FixingRevenue
    }

}