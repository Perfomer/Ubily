package com.vmedia.feature.assetdetails.presentation.recycler.delegate

import com.vmedia.core.common.android.util.diffedValue
import com.vmedia.core.common.android.util.loadCircleImage
import com.vmedia.core.common.android.util.setOnClickListener
import com.vmedia.core.common.android.util.toSpan
import com.vmedia.core.common.android.view.recycler.base.BaseListItem
import com.vmedia.core.common.android.view.recycler.base.adapterDelegateViewBinding
import com.vmedia.core.common.pure.util.cropToString
import com.vmedia.feature.assetdetails.presentation.databinding.AssetdetailsCardPublisherBinding
import com.vmedia.feature.assetdetails.presentation.recycler.listitem.PublisherListItem

internal typealias OnPublisherClickListener = () -> Unit

internal fun publisherAdapterDelegate(
    onPublisherClickListener: OnPublisherClickListener,
) = adapterDelegateViewBinding<PublisherListItem, BaseListItem, AssetdetailsCardPublisherBinding>(
    AssetdetailsCardPublisherBinding::inflate
) {
    binding.root.setOnClickListener(onPublisherClickListener)

    bind {
        val publisher = item.publisher

        binding.assetdetailsPublisherRating.diffedValue = publisher.averageRating.cropToString()
        binding.assetdetailsPublisherName.diffedValue = publisher.name
        binding.assetdetailsPublisherDescription.text = publisher.description.toSpan()
        binding.assetdetailsPublisherAvatar.loadCircleImage(publisher.avatar)
    }
}
