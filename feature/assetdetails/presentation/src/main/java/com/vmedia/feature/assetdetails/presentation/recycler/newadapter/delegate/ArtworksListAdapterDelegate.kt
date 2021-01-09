package com.vmedia.feature.assetdetails.presentation.recycler.newadapter.delegate

import com.vmedia.core.common.android.util.setOnClickListener
import com.vmedia.core.common.android.view.recycler.base.BaseListItem
import com.vmedia.core.common.android.view.recycler.base.ViewHolderOnClickListener
import com.vmedia.core.common.android.view.recycler.base.adapterDelegateViewBinding
import com.vmedia.feature.assetdetails.presentation.databinding.AssetdetailsCardArtworksBinding
import com.vmedia.feature.assetdetails.presentation.recycler.artwork.ArtworksAdapter
import com.vmedia.feature.assetdetails.presentation.recycler.newadapter.listitem.ArtworksListItem

internal typealias OnShowAllClickListener = () -> Unit

internal fun artworksListAdapterDelegate(
    onArtworkClickListener: ViewHolderOnClickListener,
    onShowArtworksClickListener: OnShowAllClickListener,
) = adapterDelegateViewBinding<ArtworksListItem, BaseListItem, AssetdetailsCardArtworksBinding>(
    AssetdetailsCardArtworksBinding::inflate
) {
    val adapter = ArtworksAdapter(onArtworkClickListener)

    binding.assetdetailsArtworksShowall.setOnClickListener(onShowArtworksClickListener)
    binding.assetdetailsArtworksList.adapter = adapter

    bind {
        adapter.items = item.items
    }
}
