package com.vmedia.core.sync.synchronizer.payout

import com.vmedia.core.common.util.ItemFilter
import com.vmedia.core.network.entity.RevenueEventDto

internal object PayoutInstanceFilter: ItemFilter<RevenueEventDto>() {

    override fun filter(item: RevenueEventDto): Boolean {
        return item is RevenueEventDto.Payout || item is RevenueEventDto.FailedPayout
    }

}