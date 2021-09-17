package com.vmedia.core.common.pure.util

import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.collections.set
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

abstract class CacheHolder {

    private val cacheTerminator = CacheTerminator()

    fun dropCache() = cacheTerminator.terminateCache()

    protected fun <T> cachedSingle(
        query: Single<T>
    ): ReadOnlyProperty<Any, Single<T>> {
        return SingleCachedProperty(query).apply { cacheTerminator.attach(this) }
    }

    protected fun <K, V> cachedMapSingle(
        queryProvider: (K) -> Single<V>
    ): ReadOnlyProperty<Any, SingleValueHolder<K, V>> {
        return MapSingleCachedProperty(queryProvider).apply { cacheTerminator.attach(this) }
    }

}

interface SingleValueHolder<K, V> {
    operator fun get(key: K): Single<V>
}

/**
 * Caches [Single]'s query results associated with keys (argument)
 *
 * You can use it like this: `myProperty[1]`
 * And the class will return cached [Single] query.
 *
 * If there's no remembered value for the exact key — [queryProvider] will be called for the received the value.
 * Else it will provide you Single.just(value)
 *
 * @property queryProvider query provider with key (argument)
 */
internal class MapSingleCachedProperty<K, V>(
    private val queryProvider: (K) -> Single<V>
) : ReadOnlyProperty<Any, SingleValueHolder<K, V>>,
    Reusable,
    SingleValueHolder<K, V> {

    private val cachedValues: MutableMap<K, CachedSingleValue<V>> = mutableMapOf()

    override fun get(key: K): Single<V> {
        if (!cachedValues.containsKey(key)) {
            cachedValues[key] = CachedSingleValue(queryProvider.invoke(key))
        }

        return cachedValues[key]!!.getValue()
    }

    override fun getValue(thisRef: Any, property: KProperty<*>) = this

    override fun drop() {
        cachedValues.values.forEach(CachedSingleValue<V>::drop)
        cachedValues.clear()
    }

}

private interface Reusable {
    fun drop()
}

/**
 * Caches [Single]'s query result
 *
 * @param source [Single] query
 */
private class SingleCachedProperty<T>(
    private val source: Single<T>
) : ReadOnlyProperty<Any, Single<T>>,
    Reusable {

    private val cache = CachedSingleValue(source)

    override fun getValue(thisRef: Any, property: KProperty<*>): Single<T> {
        return cache.getValue()
    }

    @Synchronized
    override fun drop() {
        cache.drop()
    }

}

private class CachedSingleValue<T>(
    private val source: Single<T>
) : Reusable {

    private var subject: Subject<T> = BehaviorSubject.create()
    private var isQueried = AtomicBoolean(false)

    fun getValue(): Single<T> {
        return if (isQueried.get()) {
            subject.firstOrError()
        } else {
            source.doOnSubscribe { isQueried.set(true) }
                .doOnSuccess(subject::onNext)
        }
    }

    @Synchronized
    override fun drop() {
        subject = BehaviorSubject.create()
        isQueried.set(false)
    }

}

/**
 * Cache terminator class
 *
 * You can add listeners and [attach] cache drop requests.
 * You can drop cache for all listeners by [terminateCache] method.
 */
private class CacheTerminator {

    private val listeners = mutableListOf<Reusable>()

    fun attach(action: Reusable) {
        listeners += action
    }

    fun terminateCache() {
        listeners.forEach(Reusable::drop)
    }

}
