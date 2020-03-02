package com.vmedia.core.network.api.entity.rest

import com.google.gson.annotations.SerializedName

internal data class PeriodsModel(
    @SerializedName("periods")
    val periods: List<PeriodModel>
)