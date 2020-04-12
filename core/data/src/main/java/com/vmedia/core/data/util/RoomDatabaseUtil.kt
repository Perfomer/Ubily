package com.vmedia.core.data.util

import androidx.annotation.WorkerThread
import androidx.room.RoomDatabase
import com.vmedia.core.data.internal.database.dao.base.BaseDao
import io.reactivex.Completable

/**
 * Executes the specified function [body] in a database transaction. The transaction will be
 * marked as successful unless an exception is thrown in the function.
 *
 * Room will only perform at most one transaction at a time.
 *
 * @param body The piece of code to execute.
 */
fun RoomDatabase.completableTransaction(body: () -> Unit): Completable {
    return Completable.fromAction { runInTransaction(body) }
}

@WorkerThread
fun <T> BaseDao<T>.upsert(contains: Boolean, item: T) {
    if (contains) update(item)
    else insert(item)
}