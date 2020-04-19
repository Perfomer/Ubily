package com.vmedia.core.data.internal.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    @PrimaryKey val id: Long,
    val name: String
)