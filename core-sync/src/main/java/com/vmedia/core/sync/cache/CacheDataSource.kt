package com.vmedia.core.sync.cache

import io.reactivex.Observable
import io.reactivex.Single

internal interface CacheDataSource {
    fun dropCache()
}

internal fun <T> extractWithCache(cachedValue: T?, query: Single<T>): Single<T> {
    return Single.fromCallable { cachedValue!! }.onErrorResumeNext(query)
}

internal fun <T> extractWithCache(cachedValue: T?, query: Observable<T>): Observable<T> {
    return Observable.fromCallable { cachedValue!! }.onErrorResumeNext(query)
}