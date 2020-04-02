package com.vmedia.core.data.internal.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index

@Entity(
    primaryKeys = ["eventId", "entityId"],
    indices = [
        Index(value = ["eventId"]),
        Index(value = ["entityId"])
    ],
    foreignKeys = [ForeignKey(
        entity = Event::class,
        childColumns = ["eventId"],
        parentColumns = ["id"],
        onDelete = CASCADE
    )]
)
data class EventEntity(
    val eventId: Long,
    val entityId: Long
)