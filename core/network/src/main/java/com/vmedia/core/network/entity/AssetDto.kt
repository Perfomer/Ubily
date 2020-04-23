package com.vmedia.core.network.entity

import com.vmedia.core.common.obj.AssetStatus
import com.vmedia.core.common.obj.HollowRating
import com.vmedia.core.common.obj.Money
import java.util.*

data class AssetDto(
    val id: Long,
    val packageVersionId: Long,
    val categoryId: Long,
    val name: String,
    val versionName: String,
    val shortUrl: String,
    @HollowRating val averageRating: Int,
    val reviewsQuantity: Int,
    val status: AssetStatus,
    val sizeMb: Double,
    val creationDate: Date,
    val modificationDate: Date,
    val publishingDate: Date?,
    val price: Money
)