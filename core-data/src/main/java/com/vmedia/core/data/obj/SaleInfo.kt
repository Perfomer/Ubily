package com.vmedia.core.data.obj

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
data class SaleInfo(
    val assetId: Long,
    val assetName: String,
    val assetIcon: String,
    val quantity: Int,
    val summaryPrice: BigDecimal
): Parcelable