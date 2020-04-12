package com.vmedia.core.network.api.entity

import com.google.gson.annotations.SerializedName

internal data class PeriodsModel(
    @SerializedName("periods")
    val periods: List<PeriodModel>
)