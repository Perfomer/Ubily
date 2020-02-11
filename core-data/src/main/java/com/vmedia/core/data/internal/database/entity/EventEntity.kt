package com.vmedia.core.data.internal.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = arrayOf("eventId", "entityId"), unique = true)],
    foreignKeys = [ForeignKey(
        entity = Event::class,
        childColumns = arrayOf("eventId"),
        parentColumns = arrayOf("id"),
        onDelete = CASCADE
    )]
)
data class EventEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val eventId: Long,
    val entityId: Long
)