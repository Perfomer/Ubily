package com.vmedia.core.common.view.recycler

import androidx.recyclerview.widget.DiffUtil
import com.vmedia.core.common.view.recycler.base.BaseAdapter
import com.vmedia.core.common.view.recycler.base.BaseViewHolder
import com.vmedia.core.data.KeyEntity

abstract class EntityAdapter<T : KeyEntity<*>, VH : BaseViewHolder> : BaseAdapter<VH>() {

    var items: List<T> = listOf()
        set(value) {
            val callback = EntityDiffCallback(field, value)
            val result = DiffUtil.calculateDiff(callback)

            field = value

            result.dispatchUpdatesTo(this)
        }

    override fun getItemCount() = items.size

    override fun getItemId(position: Int) = items[position].id.hashCode().toLong()

    override fun onBindViewHolder(holder: VH, position: Int) = onBindViewHolder(holder, items[position])

    abstract fun onBindViewHolder(holder: VH, item: T)

}