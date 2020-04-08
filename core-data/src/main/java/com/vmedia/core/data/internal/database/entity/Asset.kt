package com.vmedia.core.data.internal.database.entity

import androidx.annotation.IntRange
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.vmedia.core.common.obj.AssetStatus
import com.vmedia.core.common.util.EMPTY_DATE
import com.vmedia.core.data.KeyEntity
import java.math.BigDecimal
import java.util.*

@Entity(
    indices = [Index(value = ["shortUrl"], unique = true)]
)
data class Asset(
    @PrimaryKey override val id: Long,
    val categoryId: Long = 0L,
    val versionId: Long = 0L,

    val creationDate: Date = EMPTY_DATE,
    val modificationDate: Date = EMPTY_DATE,
    val publishingDate: Date = EMPTY_DATE,

    val priceUsd: BigDecimal = BigDecimal.ZERO,
    val versionName: String = "",
    val totalFileSize: Double = 0.0,
    val status: AssetStatus = AssetStatus.NONE,
    @IntRange(from = 0, to = 5) val averageRating: Int = 0,

    val name: String = "",
    val description: String = "",

    val shortUrl: String? = "",
    val bigImage: String? = "",
    val smallImage: String? = "",
    val iconImage: String? = ""
) : KeyEntity<Long>