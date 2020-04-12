package com.vmedia.feature.sync.presentation.recycler

import android.view.View
import com.vmedia.core.common.view.recycler.base.BaseAdapter
import com.vmedia.core.common.view.recycler.diffedListBy
import com.vmedia.feature.sync.presentation.R
import com.vmedia.feature.sync.presentation.model.SyncDataItem

internal class SyncAdapter : BaseAdapter<SyncViewHolder>() {

    var items: List<SyncDataItem> by diffedListBy(SyncDataItem::type)


    override fun getItemCount() = items.size

    override fun onLayoutRequested(viewType: Int) = R.layout.sync_item

    override fun onCreateViewHolder(view: View, viewType: Int) = SyncViewHolder(view)

    override fun onBindViewHolder(holder: SyncViewHolder, position: Int) {
        holder.bind(items[position])
    }

}