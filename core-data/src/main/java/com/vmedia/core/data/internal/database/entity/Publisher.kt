package com.vmedia.core.data.internal.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.vmedia.core.data.KeyEntity
import com.vmedia.core.data.obj.Money

@Entity(indices = [Index(value = ["label"], unique = true)])
data class Publisher(
    @PrimaryKey override val id: Long,
    val name: String,
    val label: String,
    val description: String,
    val email: String,
    val url: String,
    val smallImageUrl: String = "",
    val largeImageUrl: String = "",
    @Embedded val balance: Money,
    @Embedded val rssToken: RssToken
) : KeyEntity<Long>

data class RssToken(
    val token: String,
    val publisherName: String
)