package com.vmedia.core.common.obj.event

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
data class SaleInfo(
    val assetId: Long,
    val assetName: String = "",
    val assetIcon: String? = null,
    val quantity: Int = 1,
    val summaryPrice: BigDecimal = BigDecimal.ZERO
): Parcelable