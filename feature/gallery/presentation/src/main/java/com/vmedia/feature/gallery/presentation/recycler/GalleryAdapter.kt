package com.vmedia.feature.gallery.presentation.recycler

import android.view.View
import com.vmedia.core.common.android.view.recycler.base.BaseAdapter
import com.vmedia.core.common.android.view.recycler.diffedListBy
import com.vmedia.feature.gallery.presentation.R

internal class GalleryAdapter : BaseAdapter<GalleryItemViewHolder>() {

    var items by diffedListBy(String::hashCode)


    override fun getItemCount() = items.size

    override fun onLayoutRequested(viewType: Int) = R.layout.gallery_item

    override fun onCreateViewHolder(view: View, viewType: Int): GalleryItemViewHolder {
        return GalleryItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: GalleryItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

}