package com.vmedia.core.network.api.entity.rest.publisher

import com.google.gson.annotations.SerializedName

internal data class BalanceModel(
    @SerializedName("currency") val currency: String,
    @SerializedName("amount") val amount: Double,
    @SerializedName("amount_text") val amountText: String
)