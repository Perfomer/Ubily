package com.vmedia.core.data.internal.network.entity

import com.google.gson.annotations.SerializedName

data class PeriodsModel(
    @SerializedName("periods")
    val periods: List<PeriodModel>
)