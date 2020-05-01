package com.vmedia.core.data.internal.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vmedia.core.common.pure.obj.EventType
import com.vmedia.core.common.pure.util.EMPTY_DATE
import com.vmedia.core.data.KeyEntity
import java.util.*

@Entity
data class Event(
    @PrimaryKey(autoGenerate = true) override val id: Long = 0L,
    val type: EventType,
    val date: Date = EMPTY_DATE
) : KeyEntity<Long>