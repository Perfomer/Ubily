package com.vmedia.core.data.internal.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.vmedia.core.data.KeyEntity
import com.vmedia.core.network.obj.RssToken

@Entity(indices = [Index(value = ["name"], unique = true)])
data class Publisher(
    @PrimaryKey override val id: Long,
    val name: String,
    val description: String,
    val url: String,
    val smallImageUrl: String,
    val largeImageUrl: String,
    @Embedded val rssToken: RssToken
) : KeyEntity<Long>