package com.vmedia.core.common.view.recycler

import com.vmedia.core.common.view.recycler.base.BindableViewHolder
import com.vmedia.core.data.KeyEntity

abstract class BindableEntityAdapter<T : KeyEntity<*>, VH : BindableViewHolder<T>> : EntityAdapter<T, VH>() {

    override fun onBindViewHolder(holder: VH, item: T) = holder.bind(item)

}