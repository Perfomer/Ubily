package com.vmedia.core.data.internal.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["value"], unique = true)])
data class Keyword(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val value: String
)