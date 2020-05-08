package com.vmedia.feature.gallery.presentation.recycler

import android.view.View
import com.vmedia.core.common.android.view.recycler.base.BaseAdapter
import com.vmedia.core.common.android.view.recycler.diffedListBy
import com.vmedia.core.data.internal.database.entity.Artwork
import com.vmedia.feature.gallery.presentation.R

internal class GalleryPreviewAdapter(
    private val onClick: (Artwork) -> Unit
) : BaseAdapter<GalleryPreviewViewHolder>() {

    var items by diffedListBy(Artwork::id)


    override fun getItemCount() = items.size

    override fun onLayoutRequested(viewType: Int) = R.layout.gallery_preview_item

    override fun onCreateViewHolder(view: View, viewType: Int): GalleryPreviewViewHolder {
        return GalleryPreviewViewHolder(view, ::onClick)
    }

    override fun onBindViewHolder(holder: GalleryPreviewViewHolder, position: Int) {
        holder.bind(items[position])
    }


    private fun onClick(position: Int) {
        onClick.invoke(items[position])
    }

}