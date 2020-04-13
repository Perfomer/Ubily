package com.vmedia.feature.eventdetails.presentation.viewholder.asset

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.vmedia.core.common.obj.EventType
import com.vmedia.core.common.obj.event.AssetInfo
import com.vmedia.core.common.util.init
import com.vmedia.feature.eventdetails.presentation.R
import com.vmedia.feature.eventdetails.presentation.viewholder.EventDetailsViewHolder
import com.vmedia.feature.eventdetails.presentation.viewholder.asset.recycler.AssetsAdapter

internal class AssetViewHolder(
    context: Context,
    onAssetClick: (AssetInfo) -> Unit
) : EventDetailsViewHolder<List<AssetInfo>>(
    EventType.ASSET,
    context,
    R.layout.eventdetails_item_assets
) {

    private val adapter = AssetsAdapter(onAssetClick)

    init {
        val recycler = containerView as RecyclerView
        recycler.init(adapter)
        recycler.isNestedScrollingEnabled = false
    }

    override fun bind(item: List<AssetInfo>) {
        adapter.items = item
    }

}