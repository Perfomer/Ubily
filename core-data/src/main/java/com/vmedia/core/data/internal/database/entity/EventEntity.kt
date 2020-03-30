package com.vmedia.core.data.internal.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.vmedia.core.data.KeyEntity

@Entity(
    indices = [Index(value = ["eventId", "entityId"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = Event::class,
        childColumns = ["eventId"],
        parentColumns = ["id"],
        onDelete = CASCADE
    )]
)
data class EventEntity(
    @PrimaryKey(autoGenerate = true) override val id: Long,
    val eventId: Long,
    val entityId: Long
): KeyEntity<Long>