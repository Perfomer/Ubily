package com.vmedia.core.network.entity

import com.vmedia.core.network.entity.internal.RevenueEventDto.Payout
import com.vmedia.core.network.entity.internal.RevenueEventDto.Revenue

data class RevenueDto(
    val revenues: Collection<Revenue>,
    val payouts: Collection<Payout>
)