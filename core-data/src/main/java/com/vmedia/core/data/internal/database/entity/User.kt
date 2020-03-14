package com.vmedia.core.data.internal.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.vmedia.core.data.KeyEntity

@Entity(
    indices = [Index(value = ["name"], unique = true)]
)
data class User(
    @PrimaryKey(autoGenerate = true) override val id: Long = 0L,
    val name: String
) : KeyEntity<Long>