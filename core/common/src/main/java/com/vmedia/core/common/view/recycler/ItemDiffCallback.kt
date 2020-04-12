package com.vmedia.core.common.view.recycler

import androidx.recyclerview.widget.DiffUtil

open class ItemDiffCallback<Item>(
    private val oldItems: List<Item>,
    private val newItems: List<Item>,
    private val keyReceiver: (Item) -> Any
): DiffUtil.Callback() {

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]

        return keyReceiver.invoke(oldItem) == keyReceiver.invoke(newItem)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }

}