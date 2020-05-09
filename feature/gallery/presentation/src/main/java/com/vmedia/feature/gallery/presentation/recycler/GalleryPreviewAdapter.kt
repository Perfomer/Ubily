package com.vmedia.feature.gallery.presentation.recycler

import android.view.View
import com.vmedia.core.common.android.view.recycler.base.BaseAdapter
import com.vmedia.core.common.android.view.recycler.base.ViewHolderOnClick
import com.vmedia.core.common.android.view.recycler.diffedListBy
import com.vmedia.feature.gallery.presentation.R

internal class GalleryPreviewAdapter(
    private val onClick: ViewHolderOnClick
) : BaseAdapter<GalleryPreviewViewHolder>() {

    var items by diffedListBy(String::hashCode)


    override fun getItemCount() = items.size

    override fun onLayoutRequested(viewType: Int) = R.layout.gallery_preview_item

    override fun onCreateViewHolder(view: View, viewType: Int): GalleryPreviewViewHolder {
        return GalleryPreviewViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: GalleryPreviewViewHolder, position: Int) {
        holder.bind(items[position])
    }

}