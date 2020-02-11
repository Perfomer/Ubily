package com.vmedia.core.data.internal.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.vmedia.core.data.internal.database.dao.base.BaseDao
import com.vmedia.core.data.internal.database.entity.Asset
import io.reactivex.Observable

@Dao
interface AssetDao: BaseDao<Asset> {

    @Query("SELECT * FROM Asset")
    fun getAssets(): Observable<List<Asset>>

}