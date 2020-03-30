package com.vmedia.core.data.internal.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.vmedia.core.data.internal.database.dao.base.BaseDao
import com.vmedia.core.data.internal.database.entity.User
import io.reactivex.Single

@Dao
interface UserDao : BaseDao<User> {

    @Query("SELECT * FROM User WHERE name = :name")
    fun getUserByName(name: String): Single<User>

}