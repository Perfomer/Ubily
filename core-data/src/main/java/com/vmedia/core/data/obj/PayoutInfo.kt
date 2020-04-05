package com.vmedia.core.data.obj

import android.os.Parcelable
import com.vmedia.core.common.obj.Period
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
data class PayoutInfo(
    val period: Period,
    val amount: BigDecimal,
    val auto: Boolean,
    val paypal: Boolean,
    val failed: Boolean
): Parcelable