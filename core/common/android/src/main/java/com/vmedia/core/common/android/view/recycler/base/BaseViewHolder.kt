package com.vmedia.core.common.android.view.recycler.base

import android.view.View
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import kotlinx.android.extensions.LayoutContainer

typealias ViewHolderOnClickListener = (position: Int) -> Unit

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseViewHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    protected val context
        get() = itemView.context!!

    protected val resources
        get() = itemView.resources!!

    protected val hasPosition: Boolean
        get() = adapterPosition != NO_POSITION


    protected fun getString(@StringRes id: Int): String = resources.getString(id)

    protected fun ViewHolderOnClickListener.invokeWithPosition() {
        if (hasPosition) invoke(adapterPosition)
    }

}
