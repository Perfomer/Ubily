package com.vmedia.core.data.internal.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.vmedia.core.common.pure.obj.AssetStatus
import com.vmedia.core.common.pure.obj.HollowRating
import com.vmedia.core.common.pure.util.EMPTY_DATE
import java.math.BigDecimal
import java.util.*

@Entity(
    indices = [Index(value = ["shortUrl"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = Category::class,
        parentColumns = ["id"],
        childColumns = ["categoryId"]
    )]
)
data class Asset(
    @PrimaryKey val id: Long,
    val categoryId: Long = 0L,
    val versionId: Long = 0L,

    val creationDate: Date = EMPTY_DATE,
    val modificationDate: Date = EMPTY_DATE,
    val publishingDate: Date = EMPTY_DATE,

    val priceUsd: BigDecimal = BigDecimal.ZERO,
    val versionName: String = "",
    val totalFileSize: Double = 0.0,
    val status: AssetStatus = AssetStatus.NONE,
    @HollowRating val averageRating: Int = 0,

    val name: String = "",
    val description: String = "",

    val shortUrl: String? = "",
    val bigImage: String? = "",
    val smallImage: String? = "",
    val iconImage: String? = ""
)