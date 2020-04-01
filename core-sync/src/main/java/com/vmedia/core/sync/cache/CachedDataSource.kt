package com.vmedia.core.sync.cache

import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import java.io.Closeable
import kotlin.collections.set
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

internal abstract class CachedDataSource {

    private val cacheDropper = CacheDropper()

    fun dropCache() = cacheDropper.dropCache()

    internal fun <T> cachedSingle(query: Single<T>): ReadOnlyProperty<Any, Single<T>> {
        return SingleCachedProperty(query).apply { cacheDropper.listen(this) }
    }

    internal fun <K, V> cachedMapSingle(queryProvider: (K) -> Single<V>): MapSingleCachedProperty<K, V> {
        return MapSingleCachedProperty(queryProvider).apply { cacheDropper.listen(this) }
    }

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
    Closeable,
    SingleValueHolder<K, V> {

    private val cachedValues: MutableMap<K, CachedSingleValue<V>> = mutableMapOf()

    override fun get(key: K): Single<V> {
        if (!cachedValues.containsKey(key)) {
            cachedValues[key] = CachedSingleValue(queryProvider.invoke(key))
        }

        return cachedValues[key]!!.getValue()
    }

    override fun getValue(thisRef: Any, property: KProperty<*>) = this

    override fun close() {
        cachedValues.values.forEach(CachedSingleValue<V>::close)
        cachedValues.clear()
    }

}

/**
 * Caches [Single]'s query result
 *
 * @param source [Single] query
 */
private class SingleCachedProperty<T>(
    private val source: Single<T>
) : ReadOnlyProperty<Any, Single<T>>,
    Closeable {

    private val cache = CachedSingleValue(source)

    override fun getValue(thisRef: Any, property: KProperty<*>): Single<T> {
        return cache.getValue()
    }

    override fun close() {
        cache.close()
    }

}

private class CachedSingleValue<T>(
    private val source: Single<T>
): Closeable {

    private var subject: Subject<T> = BehaviorSubject.create()
    private var isQueried = false

    fun getValue(): Single<T> {
        return if (isQueried) {
            subject.firstOrError()
        } else {
            Single.fromCallable { source.blockingGet() }
                .doOnSubscribe { isQueried = true }
                .doOnSuccess(subject::onNext)
        }
    }

    override fun close() {
        subject = BehaviorSubject.create()
        isQueried = false
    }

}

/**
 * Cache dropper class
 *
 * You can add listeners and [listen] cache drop requests.
 * You can drop cache for all listeners by [dropCache] method.
 */
private class CacheDropper {

    private val listeners = mutableListOf<Closeable>()

    fun listen(action: Closeable) {
        listeners += action
    }

    fun dropCache() {
        listeners.forEach(Closeable::close)
    }

}

private interface SingleValueHolder<K, V> {
    operator fun get(key: K): Single<V>
}