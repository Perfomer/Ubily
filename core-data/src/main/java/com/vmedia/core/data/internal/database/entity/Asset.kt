package com.vmedia.core.data.internal.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vmedia.core.common.obj.AssetStatus
import com.vmedia.core.data.KeyEntity
import java.util.*

@Entity
data class Asset(
    @PrimaryKey override val id: Long,
    val creationDate: Date,
    val modificationDate: Date,
    val publishingDate: Date,
    val categoryId: Long,
    val versionId: Long,
    val price: Double,
    val totalFileSize: Double,
    val name: String,
    val description: String,
    val version: String,
    val shortUrl: String,
    val bigImage: String,
    val smallImage: String,
    val iconImage: String,
    val status: AssetStatus
) : KeyEntity<Long>