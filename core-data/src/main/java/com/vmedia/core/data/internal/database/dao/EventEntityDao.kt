package com.vmedia.core.data.internal.database.dao

import androidx.room.Dao
import com.vmedia.core.data.internal.database.dao.base.BaseDao
import com.vmedia.core.data.internal.database.entity.EventEntity

@Dao
interface EventEntityDao : BaseDao<EventEntity> {
}