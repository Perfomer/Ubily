package com.vmedia.core.common.pure.util.rx

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith
import kotlin.reflect.KClass

fun <T : Any> Single<List<List<T>>>.flatten(): Single<List<T>> {
    return map { it.flatten() }
}

fun <T : Any, R : Any> Single<List<T>>.mapItems(action: (T) -> R): Single<List<R>> {
    return map { it.map(action::invoke) }
}

fun <T : Any> Single<List<T>>.filterItems(predicate: (T) -> Boolean): Single<List<T>> {
    return map { it.filter(predicate::invoke) }
}

fun <T : Any, R : Any> Single<List<T>>.filterItemsAreInstance(clazz: KClass<R>): Single<List<R>> {
    return map { it.filterIsInstance(clazz.java) }
}

fun <T : Any> Single<T>.actOnSuccess(action: (T) -> Completable): Single<T> {
    return flatMap { action.invoke(it).toSingleDefault(it) }
}

fun <T : Any> Single<T>.blockingNullable(): T? {
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
