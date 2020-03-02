package com.vmedia.core.network.api.entity

import com.vmedia.core.common.obj.AssetStatus
import com.vmedia.core.common.obj.Money
import java.util.*

data class AssetDto(
    val id: Long,
    val packageVersionId: Long,
    val categoryId: Long,
    val name: String,
    val shortUrl: String,
    val averageRating: Int,
    val reviewsQuantity: Int,
    val status: AssetStatus,
    val sizeBytes: Long,
    val creationDate: Date,
    val modificationDate: Date,
    val publishingDate: Date?,
    val price: Money
)