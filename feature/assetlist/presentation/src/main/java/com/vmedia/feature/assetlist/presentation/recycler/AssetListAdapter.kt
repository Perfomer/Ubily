package com.vmedia.feature.assetlist.presentation.recycler

import android.view.View
import com.vmedia.core.common.view.recycler.base.BaseAdapter
import com.vmedia.core.common.view.recycler.diffedListBy
import com.vmedia.feature.assetlist.domain.model.AssetShortInfo
import com.vmedia.feature.assetlist.presentation.R

internal class AssetListAdapter(
    private val onClick: (AssetShortInfo) -> Unit
) : BaseAdapter<AssetViewHolder>() {

    var items by diffedListBy(AssetShortInfo::id)


    override fun getItemCount() = items.size

    override fun getItemId(position: Int) = items[position].id

    override fun onLayoutRequested(viewType: Int) = R.layout.assetlist_item

    override fun onCreateViewHolder(view: View, viewType: Int): AssetViewHolder {
        return AssetViewHolder(view, ::onClick)
    }

    override fun onBindViewHolder(holder: AssetViewHolder, position: Int) {
        holder.bind(items[position])
    }


    private fun onClick(position: Int) {
        onClick.invoke(items[position])
    }

}