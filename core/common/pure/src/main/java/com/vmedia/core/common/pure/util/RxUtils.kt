package com.vmedia.core.common.pure.util

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.zipWith
import kotlin.reflect.KClass

fun <T> ObservableSource<T>.toObservable() = Observable.wrap(this)

fun <T> T.toObservable() = Observable.just<T>(this)

inline fun <T, U, R> Observable<T>.flatWithLatestFrom(
    other: ObservableSource<U>,
    crossinline combiner: (T, U) -> ObservableSource<out R>
): Observable<R> {
    val biFunction = BiFunction<T, U, ObservableSource<out R>> { t, u -> combiner.invoke(t, u) }
    return withLatestFrom(other, biFunction).flatMap { it }
}

fun <T, R> Observable<List<T>>.mapItems(action: (T) -> R): Observable<List<R>> {
    return map { it.map(action::invoke) }
}

fun <T> Observable<List<T>>.toFlattenList(): Single<List<T>> {
    return toList().flatten()
}

fun <T> Observable<T>.blockingNullable(): T? {
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



fun <T> Single<List<List<T>>>.flatten(): Single<List<T>> {
    return map { it.flatten() }
}

fun <T, R> Single<List<T>>.mapItems(action: (T) -> R): Single<List<R>> {
    return map { it.map(action::invoke) }
}

fun <T> Single<List<T>>.filterItems(predicate: (T) -> Boolean): Single<List<T>> {
    return map { it.filter(predicate::invoke) }
}

fun <T, R : Any> Single<List<T>>.filterItemsAreInstance(clazz: KClass<R>): Single<List<R>> {
    return map { it.filterIsInstance(clazz.java) }
}

fun <T> Single<T>.actOnSuccess(action: (T) -> Completable): Single<T> {
    return flatMap { action.invoke(it).andThen(Single.just(it)) }
}

fun <T> Single<T>.blockingNullable(): T? {
    return try {
        blockingGet()
    } catch (e: Exception) {
        null
    }
}

fun <T1 : Any, T2 : Any> Single<List<T1>>.associateWith(
    companionSource: (T1) -> Single<T2>
): Single<List<Pair<T1, T2>>> {
    return this
        .flatMapObservable {
            Observable
                .fromIterable(it)
                .zipWith(companionSource::invoke)
        }
        .toList()
}

fun <T1 : Any, T2 : Any> Single<T1>.zipWith(
    companionSource: (T1) -> Single<T2>
): Single<Pair<T1, T2>> {
    return flatMap { Single.just(it).zipWith(companionSource.invoke(it)) }
}


fun Completable.andThenMerge(vararg completables: Completable): Completable {
    return andThen(Completable.mergeArray(*completables))
}