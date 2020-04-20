package com.vmedia.feature.assetdetails.domain.model

import android.os.Parcelable
import com.vmedia.core.common.obj.AssetStatus
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
data class AssetDetails(
    val id: Long = 0L,
    val categoryName: String = "",
    val name: String = "",
    val versionName: String = "",
    val priceUsd: BigDecimal = BigDecimal.ZERO,
    val status: AssetStatus = AssetStatus.NONE,
    val sizeMb: Double = 0.0,
    val description: String = "",
    val bigImage: String = "",
    val iconImage: String = ""
) : Parcelable