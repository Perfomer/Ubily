package com.vmedia.feature.eventdetails.presentation.viewholder.asset.recycler

import android.view.View
import com.vmedia.core.common.obj.event.AssetInfo
import com.vmedia.core.common.view.recycler.base.BaseAdapter
import com.vmedia.feature.eventdetails.presentation.R

internal class AssetsAdapter(
    private val onAssetClick: (assetId: Long) -> Unit
) : BaseAdapter<AssetItemViewHolder>() {

    var items: List<AssetInfo> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun getItemCount() = items.size

    override fun onLayoutRequested(viewType: Int) = R.layout.eventdetails_item_asset_item

    override fun onCreateViewHolder(view: View, viewType: Int): AssetItemViewHolder {
        return AssetItemViewHolder(view, ::onClick)
    }

    override fun onBindViewHolder(holder: AssetItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    private fun onClick(position: Int) = onAssetClick.invoke(items[position].id)

}