package com.vmedia.feature.assetdetails.presentation.recycler.delegate

import com.vmedia.core.common.android.util.addSystemTopPadding
import com.vmedia.core.common.android.util.diffedValue
import com.vmedia.core.common.android.util.disableTouches
import com.vmedia.core.common.android.util.labelResource
import com.vmedia.core.common.android.util.loadCircleImage
import com.vmedia.core.common.android.util.loadImage
import com.vmedia.core.common.android.util.setOnClickListener
import com.vmedia.core.common.android.view.recycler.base.BaseListItem
import com.vmedia.core.common.android.view.recycler.base.adapterDelegateViewBinding
import com.vmedia.core.common.pure.util.cropToString
import com.vmedia.feature.assetdetails.presentation.R
import com.vmedia.feature.assetdetails.presentation.databinding.AssetdetailsCardAssetBinding
import com.vmedia.feature.assetdetails.presentation.recycler.listitem.AssetListItem

internal typealias OnBackArrowClickListener = () -> Unit
internal typealias OnAssetIconClickListener = () -> Unit
internal typealias OnExternalLinkClickListener = () -> Unit

internal fun assetAdapterDelegate(
    onAssetIconClickListener: OnAssetIconClickListener,
    onBackArrowClickListener: OnBackArrowClickListener,
    onExternalLinkClickListener: OnExternalLinkClickListener,
) = adapterDelegateViewBinding<AssetListItem, BaseListItem, AssetdetailsCardAssetBinding>(
    AssetdetailsCardAssetBinding::inflate
) {
    binding.assetdetailsBack.setOnClickListener(onBackArrowClickListener)
    binding.assetdetailsIcon.setOnClickListener(onAssetIconClickListener)
    binding.assetdetailsExternallink.setOnClickListener(onExternalLinkClickListener)

    binding.assetdetailsAsset.disableTouches()
    binding.assetdetailsToolbar.addSystemTopPadding()

    bind {
        with(item.asset) {
            binding.assetdetailsAssetCard.assetdetailsName.diffedValue = name
            binding.assetdetailsAssetCard.assetdetailsIdentifier.diffedValue = id.toString()
            binding.assetdetailsAssetCard.assetdetailsCategory.diffedValue = categoryName
            binding.assetdetailsAssetCard.assetdetailsPrice.diffedValue = "$$priceUsd"
            binding.assetdetailsAssetCard.assetdetailsVersion.diffedValue = versionName
            binding.assetdetailsAssetCard.assetdetailsStatus.diffedValue = context.getString(status.labelResource)
            binding.assetdetailsAssetCard.assetdetailsSize.diffedValue = context.getString(
                R.string.assetdetails_asset_value_size,
                sizeMb.cropToString()
            )

            binding.assetdetailsIcon.loadCircleImage(iconImage)
            binding.assetdetailsLargeimage.loadImage(bigImage)
        }
    }
}
