package com.vmedia.core.data.obj

import android.os.Parcelable
import com.vmedia.core.common.obj.Period
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
data class RevenueInfo(
    val period: Period,
    val amount: BigDecimal = BigDecimal.ZERO,
    val sale: Boolean = false,
    val revenueDelta: Double = 0.0
): Parcelable