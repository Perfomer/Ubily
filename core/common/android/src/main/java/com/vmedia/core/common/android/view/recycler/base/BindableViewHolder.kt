package com.vmedia.core.common.android.view.recycler.base

import android.view.View

abstract class BindableViewHolder<T>(
    containerView: View
) : BaseViewHolder(containerView) {

    abstract fun bind(item: T)

}