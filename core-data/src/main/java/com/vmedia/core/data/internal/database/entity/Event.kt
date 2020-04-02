package com.vmedia.core.data.internal.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vmedia.core.data.KeyEntity
import java.util.*

@Entity
data class Event(
    @PrimaryKey(autoGenerate = true) override val id: Long = 0L,
    val type: EventType,
    val date: Date
): KeyEntity<Long>

enum class EventType {
    SALE,
    FREE_DOWNLOAD,
    REVIEW,
    ASSET,
    PAYOUT,
    REVENUE,
    ANNIVERSARY_SALE,
    INITIALIZATION
}