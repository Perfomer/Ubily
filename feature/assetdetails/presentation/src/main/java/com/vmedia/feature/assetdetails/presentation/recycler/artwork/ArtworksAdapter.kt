package com.vmedia.feature.assetdetails.presentation.recycler.artwork

import android.view.View
import com.vmedia.core.common.view.recycler.base.BaseAdapter
import com.vmedia.core.common.view.recycler.base.ViewHolderOnClick
import com.vmedia.core.common.view.recycler.diffedListBy
import com.vmedia.feature.assetdetails.presentation.R

internal class ArtworksAdapter(
    private val onClick: ViewHolderOnClick
) : BaseAdapter<ArtworkViewHolder>() {

    var items by diffedListBy(String::hashCode)


    override fun getItemCount() = items.size

    override fun onLayoutRequested(viewType: Int) = R.layout.assetdetails_item_artwork

    override fun onCreateViewHolder(view: View, viewType: Int): ArtworkViewHolder {
        return ArtworkViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ArtworkViewHolder, position: Int) {
        holder.bind(items[position])
    }

}