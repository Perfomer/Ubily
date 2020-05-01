package com.vmedia.feature.gallery.presentation.recycler

import android.view.View
import com.vmedia.core.common.view.recycler.base.BaseAdapter
import com.vmedia.core.common.view.recycler.diffedListBy
import com.vmedia.core.data.internal.database.entity.Artwork
import com.vmedia.feature.gallery.presentation.R

internal class GalleryAdapter(
    private val onClick: (Artwork) -> Unit
) : BaseAdapter<GalleryItemViewHolder>() {

    var items by diffedListBy(Artwork::id)


    override fun getItemCount() = items.size

    override fun onLayoutRequested(viewType: Int) = R.layout.gallery_item

    override fun onCreateViewHolder(view: View, viewType: Int): GalleryItemViewHolder {
        return GalleryItemViewHolder(view, ::onClick)
    }

    override fun onBindViewHolder(holder: GalleryItemViewHolder, position: Int) {
        holder.bind(items[position])
    }


    private fun onClick(position: Int) {
        onClick.invoke(items[position])
    }

}