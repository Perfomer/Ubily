package com.vmedia.feature.assetdetails.domain.model

import android.os.Parcelable
import com.vmedia.core.common.obj.AssetStatus
import com.vmedia.core.common.obj.HollowRating
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
data class DetailedAsset(
    val id: Long = 0L,
    val categoryName: String = "",
    val name: String = "",
    val shortUrl: String? = null,
    val versionName: String = "",
    @HollowRating val averageRating: Int = 1,
    val priceUsd: BigDecimal = BigDecimal.ZERO,
    val status: AssetStatus = AssetStatus.NONE,
    val sizeMb: Double = 0.0,
    val description: String = "",
    val bigImage: String? = null,
    val iconImage: String? = null,
    val artworks: List<String> = emptyList(),
    val keywords: List<KeywordModel> = emptyList()
) : Parcelable