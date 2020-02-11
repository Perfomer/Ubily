package com.vmedia.core.data.internal.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Event(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val type: EventType,
    val date: Date
)

enum class EventType {
    SALE,
    FREE_DOWNLOAD,
    COMMENT,
    UNRESOLVED_COMMENT,
    ASSET,
    PAYOUT,
    REVENUE,
    ANNIVERSARY_SALE,
    INITIALIZATION
}