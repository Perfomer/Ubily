package com.vmedia.feature.assetdetails.domain.model

import com.vmedia.core.common.android.obj.AssetStatus
import com.vmedia.core.common.android.obj.HollowRating
import com.vmedia.core.data.internal.database.entity.Artwork
import java.math.BigDecimal

data class DetailedAsset(
    val id: Long = 0L,
    val categoryName: String = "",
    val name: String = "",
    val shortUrl: String? = null,
    val versionName: String = "",
    @HollowRating val averageRating: Int = 0,
    val priceUsd: BigDecimal = BigDecimal.ZERO,
    val status: AssetStatus = AssetStatus.NONE,
    val sizeMb: Double = 0.0,
    val description: String = "",
    val bigImage: String? = null,
    val iconImage: String? = null,
    val artworks: List<Artwork> = emptyList(),
    val keywords: List<KeywordModel> = emptyList()
)