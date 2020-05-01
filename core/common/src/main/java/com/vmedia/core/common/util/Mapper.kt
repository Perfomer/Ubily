package com.vmedia.core.common.util

import androidx.annotation.WorkerThread
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Mapper interface
 * Converts an object from [FROM] type to [TO] type
 *
 * @param FROM source type
 * @param TO result type
 */
@FunctionalInterface
interface Mapper<FROM, TO> {

    @WorkerThread
    fun map(from: FROM): TO

}

/**
 * List Mapper class
 * Implements [Mapper] interface
 *
 * @param FROM source type of list items
 * @param TO result type of list items
 */
abstract class ListMapper<FROM, TO> : Mapper<List<FROM>, List<TO>> {

    final override fun map(from: List<FROM>): List<TO> {
        return mutableListOf<TO>().apply {
            addAll(from.map(::mapItem))
        }
    }

    protected abstract fun mapItem(from: FROM): TO

}

interface ObservableMapper<FROM, TO> : Mapper<FROM, Observable<TO>>

abstract class ObservableListMapper<FROM, TO> : Mapper<List<FROM>, Observable<List<TO>>> {

    override fun map(from: List<FROM>): Observable<List<TO>> {
        return Observable.defer {
            Observable.fromIterable(from)
                .concatMap(::mapItem)
                .toList()
                .toObservable()
        }
    }

    abstract fun mapItem(from: FROM): Observable<TO>

}

fun <FROM, TO> ObservableMapper<FROM, TO>.toListMapper(): ObservableListMapper<FROM, TO> {
    return object : ObservableListMapper<FROM, TO>() {
        override fun mapItem(from: FROM) = map(from)
    }
}

/**
 * Automatically turns simple mapper to list mapper
 *
 * @receiver simple mapper
 * @return list mapper
 */
fun <FROM, TO> Mapper<FROM, TO>.toListMapper(): ListMapper<FROM, TO> {
    return object : ListMapper<FROM, TO>() {
        override fun mapItem(from: FROM): TO = map(from)
    }
}

fun <FROM, TO> Observable<FROM>.mapWith(mapper: Mapper<FROM, TO>): Observable<TO> {
    return map(mapper::map)
}

fun <FROM, TO> Observable<FROM>.flatMapWith(mapper: ObservableMapper<FROM, TO>): Observable<TO> {
    return flatMap(mapper::map)
}

fun <FROM, TO> Observable<List<FROM>>.flatMapWith(mapper: ObservableListMapper<FROM, TO>): Observable<List<TO>> {
    return flatMap(mapper::map)
}

fun <FROM, TO> Single<FROM>.mapWith(mapper: Mapper<FROM, TO>): Single<TO> {
    return map(mapper::map)
}