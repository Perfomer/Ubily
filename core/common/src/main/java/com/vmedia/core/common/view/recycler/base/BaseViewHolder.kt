package com.vmedia.core.common.view.recycler.base

import android.view.View
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import kotlinx.android.extensions.LayoutContainer

typealias ViewHolderOnClick = (position: Int) -> Unit

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseViewHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    protected val context by lazy { itemView.context!! }

    protected val resources by lazy { itemView.resources!! }

    protected val hasPosition: Boolean
        get() = adapterPosition != NO_POSITION


    protected fun getString(@StringRes id: Int): String = resources.getString(id)

    @Deprecated("Use another method BaseViewHolder#invokeWithPosition")
    protected fun <T> ((T) -> Unit).safeInvoke(item: T) {
        if (hasPosition) invoke(item)
    }

    protected fun ViewHolderOnClick.invokeWithPosition() {
        if (hasPosition) invoke(adapterPosition)
    }

}