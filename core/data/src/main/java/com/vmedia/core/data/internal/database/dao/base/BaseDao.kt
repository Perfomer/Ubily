package com.vmedia.core.data.internal.database.dao.base

import androidx.annotation.WorkerThread
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @WorkerThread
    @Update
    fun update(item: T)

    @WorkerThread
    @Update
    fun update(items: List<T>)

    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWithReplace(item: T): Long

    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWithReplace(items: Collection<T>): List<Long>

    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: T): Long

    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(items: Collection<T>): List<Long>

    @WorkerThread
    @Delete
    fun delete(item: T)

    @WorkerThread
    @Delete
    fun delete(items: List<T>)

}