package com.vmedia.core.data.internal.database.dao.base

import androidx.annotation.WorkerThread
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @WorkerThread
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(item: T)

    @WorkerThread
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(items: List<T>)

    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: T)

    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<T>)

    @WorkerThread
    @Delete
    fun delete(item: T)

    @WorkerThread
    @Delete
    fun delete(items: List<T>)

}