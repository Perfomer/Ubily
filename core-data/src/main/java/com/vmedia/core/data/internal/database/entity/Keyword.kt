package com.vmedia.core.data.internal.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.vmedia.core.data.KeyEntity

@Entity(
    indices = [Index(value = ["value"], unique = true)]
)
data class Keyword(
    @PrimaryKey(autoGenerate = true) override val id: Long = 0L,
    val value: String
): KeyEntity<Long>