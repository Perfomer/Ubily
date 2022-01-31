package com.vmedia.core.data.internal.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["name"], unique = true)]
)
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val name: String
)