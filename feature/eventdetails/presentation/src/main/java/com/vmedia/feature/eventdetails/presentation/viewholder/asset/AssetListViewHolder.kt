package com.vmedia.feature.eventdetails.presentation.viewholder.asset

import android.content.Context
import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.vmedia.core.common.android.obj.EventType
import com.vmedia.core.common.android.util.init
import com.vmedia.feature.eventdetails.presentation.R
import com.vmedia.feature.eventdetails.presentation.viewholder.EventDetailsViewHolder
import com.vmedia.feature.eventdetails.presentation.viewholder.asset.recycler.AssetsAdapter
import com.vmedia.feature.eventdetails.presentation.viewholder.asset.recycler.BaseAssetItemViewHolder

internal abstract class AssetListViewHolder<T : Any, VH : BaseAssetItemViewHolder<T>>(
    context: Context,
    eventType: EventType,
    @LayoutRes private val itemLayoutResource: Int
) : EventDetailsViewHolder<List<T>>(
    eventType,
    context,
    R.layout.eventdetails_item_assets
) {

    protected val items: List<T>
        get() = adapter.items

    private val adapter = AssetsAdapter(itemLayoutResource, ::onViewHolderCreate)

    init {
        val recycler = containerView as RecyclerView
        recycler.init(adapter)
        recycler.isNestedScrollingEnabled = false
    }

    override fun bind(item: List<T>) {
        adapter.items = item
    }

    protected abstract fun onViewHolderCreate(view: View): VH


}