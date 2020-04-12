package com.vmedia.core.common.view.recycler

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T> diffedListBy(
    identifierReceiver: (T) -> Any
): RecyclerItemsEntityDiffCallbackDelegate<T> {
    return RecyclerItemsEntityDiffCallbackDelegate(identifierReceiver)
}

class RecyclerItemsEntityDiffCallbackDelegate<T>(
    private val identifierReceiver: (T) -> Any
) : ReadWriteProperty<RecyclerView.Adapter<*>, List<T>> {

    private var value: List<T> = emptyList()

    override fun getValue(thisRef: RecyclerView.Adapter<*>, property: KProperty<*>): List<T> {
        return value
    }

    override fun setValue(
        thisRef: RecyclerView.Adapter<*>,
        property: KProperty<*>,
        value: List<T>
    ) {
        val callback = ItemDiffCallback(this.value, value, identifierReceiver::invoke)
        val result = DiffUtil.calculateDiff(callback)

        // just copy the list
        this.value = value.toList()

        result.dispatchUpdatesTo(thisRef)
    }

}