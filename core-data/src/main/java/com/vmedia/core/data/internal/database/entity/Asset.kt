package com.vmedia.core.data.internal.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vmedia.core.common.obj.AssetStatus
import com.vmedia.core.data.KeyEntity
import java.math.BigDecimal
import java.util.*

@Entity
data class Asset(
    @PrimaryKey override val id: Long,
    val categoryId: Long,
    val versionId: Long,

    val creationDate: Date,
    val modificationDate: Date,
    val publishingDate: Date?,

    val priceUsd: BigDecimal,
    val totalFileSize: Double,
    val status: AssetStatus,

    val name: String,
    val description: String,

    val shortUrl: String?,
    val bigImage: String?,
    val smallImage: String?,
    val iconImage: String?
) : KeyEntity<Long>