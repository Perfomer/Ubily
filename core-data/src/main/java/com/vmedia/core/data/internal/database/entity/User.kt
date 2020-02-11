package com.vmedia.core.data.internal.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vmedia.core.data.KeyEntity

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) override val id: Long,
    val name: String
) : KeyEntity<Long>