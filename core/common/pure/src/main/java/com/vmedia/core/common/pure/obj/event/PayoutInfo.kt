package com.vmedia.core.common.pure.obj.event

import android.os.Parcelable
import com.vmedia.core.common.pure.obj.Period
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
data class PayoutInfo(
    val period: Period,
    val amount: BigDecimal = BigDecimal.ZERO,
    val auto: Boolean = false,
    val paypal: Boolean = false,
    val failed: Boolean = false
) : Parcelable