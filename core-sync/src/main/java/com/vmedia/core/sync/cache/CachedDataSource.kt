package com.vmedia.core.sync.cache

import com.vmedia.core.common.util.get
import com.vmedia.core.sync.cache.CachedValue.Invalid
import com.vmedia.core.sync.cache.CachedValue.Value
import io.reactivex.Single
import java.io.Closeable
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

    private val cachedValues: MutableMap<K, CachedValue<V>> = mutableMapOf()

    override fun get(key: K): Single<V> {
        return when (val currentValue = cachedValues.get(key, Invalid)) {
            Invalid -> {
                queryProvider.invoke(key).doOnSuccess { cachedValues[key] = Value(it) }
            }

            is Value<V> -> {
                Single.just(currentValue.value)
            }
        }
    }

    override fun getValue(thisRef: Any, property: KProperty<*>) = this

    override fun close() = cachedValues.clear()

}

/**
 * Caches [Single]'s query result
 *
 * @param query [Single] query
 */
private class SingleCachedProperty<T>(
    private val query: Single<T>
) : ReadOnlyProperty<Any, Single<T>>, Closeable {

    private var cachedValue: CachedValue<T> = Invalid

    override fun getValue(thisRef: Any, property: KProperty<*>): Single<T> {
        return when (val currentValue = cachedValue) {
            Invalid -> {
                query.doOnSuccess { cachedValue = Value(it) }
            }

            is Value<T> -> {
                Single.just(currentValue.value)
            }
        }
    }

    override fun close() {
        cachedValue = Invalid
    }

}

/**
 * Two states of the cached data:
 * - [Invalid] (there's no any cached value)
 * - [Value] (there is cached value)
 */
private sealed class CachedValue<out T> {
    object Invalid : CachedValue<Nothing>()
    class Value<out T>(val value: T) : CachedValue<T>()
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