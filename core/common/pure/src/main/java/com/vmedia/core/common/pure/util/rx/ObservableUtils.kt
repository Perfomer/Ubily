package com.vmedia.core.common.pure.util.rx

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.zipWith

fun <T : Any> ObservableSource<T>.toObservable() = Observable.wrap(this)

fun <T : Any> T.toObservable() = Observable.just(this)

inline fun <T : Any, U : Any, R : Any> Observable<T>.flatWithLatestFrom(
    other: ObservableSource<U>,
    crossinline combiner: (T, U) -> ObservableSource<out R>
): Observable<R> {
    val biFunction = BiFunction<T, U, ObservableSource<out R>> { t, u -> combiner.invoke(t, u) }
    return withLatestFrom(other, biFunction).flatMap { it }
}

fun <T : Any> Observable<T>.blockingNullable(): T? {
    return try {
        blockingSingle()
    } catch (e: Exception) {
        null
    }
}

fun <T1 : Any, T2 : Any> Observable<T1>.zipWith(
    companionSource: (T1) -> Single<T2>
): Observable<Pair<T1, T2>> {
    return flatMapSingle { Single.just(it).zipWith(companionSource.invoke(it)) }
}

fun <T : Any, R : Any> Observable<List<T>>.mapItems(action: (T) -> R): Observable<List<R>> {
    return map { it.map(action::invoke) }
}

fun <T : Any> Observable<List<T>>.toFlattenList(): Single<List<T>> {
    return toList().flatten()
}
