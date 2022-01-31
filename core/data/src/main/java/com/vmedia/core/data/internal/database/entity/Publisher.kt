package com.vmedia.core.data.internal.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.vmedia.core.common.pure.obj.creds.RssToken
import java.math.BigDecimal

@Entity(indices = [Index(value = ["name"], unique = true)])
data class Publisher(
    @PrimaryKey val id: Long,
    @Embedded val rssToken: RssToken = RssToken(),

    val name: String = "",
    val description: String = "",
    val url: String = "",

    val smallImageUrl: String = "",
    val largeImageUrl: String = "",

    val balanceUsd: BigDecimal = BigDecimal.ZERO
)