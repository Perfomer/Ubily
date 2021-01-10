package com.vmedia.feature.assetdetails.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.google.android.material.appbar.AppBarLayout
import com.vmedia.core.common.android.util.addSystemTopPadding
import com.vmedia.core.common.android.util.diffedValue
import com.vmedia.core.common.android.util.inflater
import com.vmedia.core.common.android.util.labelResource
import com.vmedia.core.common.android.util.loadCircleImage
import com.vmedia.core.common.android.util.loadImage
import com.vmedia.core.common.android.util.setOnClickListener
import com.vmedia.core.common.pure.util.cropToString
import com.vmedia.feature.assetdetails.domain.model.DetailedAsset
import com.vmedia.feature.assetdetails.presentation.R
import com.vmedia.feature.assetdetails.presentation.databinding.AssetdetailsViewHeaderBinding

internal typealias OnBackArrowClickListener = () -> Unit
internal typealias OnAssetIconClickListener = () -> Unit
internal typealias OnExternalLinkClickListener = () -> Unit

internal class AssetDetailsHeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr),
    AppBarLayout.OnOffsetChangedListener {

    internal var onAssetIconClickListener: OnAssetIconClickListener? = null
    internal var onBackArrowClickListener: OnBackArrowClickListener? = null
    internal var onExternalLinkClickListener: OnExternalLinkClickListener? = null

    internal var asset: DetailedAsset? = null
        set(value) {
            if (field == value) return
            field = value
            onAssetUpdate()
        }

    private val binding = AssetdetailsViewHeaderBinding.inflate(inflater, this, true)

    init {
        binding.assetdetailsBack.setOnClickListener(onBackArrowClickListener)
        binding.assetdetailsIcon.setOnClickListener(onAssetIconClickListener)
        binding.assetdetailsExternallink.setOnClickListener(onExternalLinkClickListener)

        binding.assetdetailsToolbar.addSystemTopPadding()
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        val progress = -verticalOffset / appBarLayout!!.totalScrollRange.toFloat()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        (parent as? AppBarLayout)?.addOnOffsetChangedListener(this)
    }

    private fun onAssetUpdate() {
        with(asset ?: return) {
            binding.assetdetailsName.diffedValue = name
            binding.assetdetailsIdentifier.diffedValue = id.toString()
            binding.assetdetailsCategory.diffedValue = categoryName
            binding.assetdetailsPrice.diffedValue = "$$priceUsd"
            binding.assetdetailsVersion.diffedValue = versionName
            binding.assetdetailsStatus.diffedValue = context.getString(status.labelResource)
            binding.assetdetailsSize.diffedValue = context.getString(
                R.string.assetdetails_asset_value_size,
                sizeMb.cropToString()
            )

            binding.assetdetailsIcon.loadCircleImage(iconImage)
            binding.assetdetailsLargeimage.loadImage(bigImage)
        }
    }
}
