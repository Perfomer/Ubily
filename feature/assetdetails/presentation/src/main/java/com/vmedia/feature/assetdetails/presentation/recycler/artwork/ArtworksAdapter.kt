package com.vmedia.feature.assetdetails.presentation.recycler.artwork

import android.view.View
import com.vmedia.core.common.android.view.recycler.base.BaseAdapter
import com.vmedia.core.common.android.view.recycler.base.ViewHolderOnClickListener
import com.vmedia.core.common.android.view.recycler.diffedListBy
import com.vmedia.core.data.internal.database.entity.Artwork
import com.vmedia.feature.assetdetails.presentation.R

internal class ArtworksAdapter(
    private val onClickListener: ViewHolderOnClickListener
) : BaseAdapter<ArtworkViewHolder>() {

    var items by diffedListBy(Artwork::id)

    init {
        setHasStableIds(true)
    }

    override fun getItemCount() = items.size

    override fun getItemId(position: Int) = items[position].id

    override fun onLayoutRequested(viewType: Int) = R.layout.assetdetails_item_artwork

    override fun onCreateViewHolder(view: View, viewType: Int): ArtworkViewHolder {
        return ArtworkViewHolder(view, onClickListener)
    }

    override fun onBindViewHolder(holder: ArtworkViewHolder, position: Int) {
        holder.bind(items[position])
    }

}
