package com.vmedia.core.sync.cache

import io.reactivex.Observable
import io.reactivex.Single

internal interface CacheDataSource {
    fun dropCache()
}

internal fun <T> extractWithCache(cachedValue: T?, query: Single<T>, callback: (T) -> Unit): Single<T> {
    return Single.fromCallable { cachedValue!! }.onErrorResumeNext(query.doOnSuccess(callback::invoke))
}

internal fun <T> extractWithCache(cachedValue: T?, query: Observable<T>, callback: (T) -> Unit): Observable<T> {
    return Observable.fromCallable { cachedValue!! }.onErrorResumeNext(query.doOnNext(callback::invoke))
}