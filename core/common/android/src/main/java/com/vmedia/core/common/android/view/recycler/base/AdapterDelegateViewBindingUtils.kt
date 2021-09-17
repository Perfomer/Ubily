package com.vmedia.core.common.android.view.recycler.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.AdapterDelegateViewBindingViewHolder
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

typealias ViewBindingProvider<Binding> = (layoutInflater: LayoutInflater, root: ViewGroup, attachToParent: Boolean) -> Binding

inline fun <reified Item : BaseItem, BaseItem : BaseListItem, Binding : ViewBinding> adapterDelegateViewBinding(
    noinline viewBinding: ViewBindingProvider<Binding>,
    noinline block: AdapterDelegateViewBindingViewHolder<Item, Binding>.() -> Unit
): AdapterDelegate<List<BaseItem>> {
    return adapterDelegateViewBinding(
        viewBinding = { layoutInflater, parent ->
            viewBinding.invoke(
                layoutInflater,
                parent,
                false
            )
        },
        block = block
    )
}
