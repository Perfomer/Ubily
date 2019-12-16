package com.vmedia.core.data.internal.network.entity.publisher

import com.google.gson.annotations.SerializedName

data class BalanceModel(
    @SerializedName("currency") val currency: String,
    @SerializedName("amount") val amount: Double,
    @SerializedName("amount_text") val amountText: String
)