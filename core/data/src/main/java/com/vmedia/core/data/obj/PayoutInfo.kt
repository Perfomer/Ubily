package com.vmedia.core.data.obj

import android.os.Parcelable
import com.vmedia.core.common.obj.Period
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
data class PayoutInfo(
    val period: Period,
    val amount: BigDecimal = BigDecimal.ZERO,
    val auto: Boolean = false,
    val paypal: Boolean = false,
    val failed: Boolean = false
): Parcelable