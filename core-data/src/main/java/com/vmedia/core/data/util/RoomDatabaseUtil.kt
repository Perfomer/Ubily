package com.vmedia.core.data.util

import androidx.room.RoomDatabase
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