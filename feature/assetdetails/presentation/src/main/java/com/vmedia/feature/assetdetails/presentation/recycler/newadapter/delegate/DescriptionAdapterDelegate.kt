package com.vmedia.feature.assetdetails.presentation.recycler.newadapter.delegate

import android.text.method.LinkMovementMethod
import androidx.core.view.isVisible
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.vmedia.core.common.android.util.init
import com.vmedia.core.common.android.util.loadImage
import com.vmedia.core.common.android.util.setOnClickListener
import com.vmedia.core.common.android.util.toSpan
import com.vmedia.core.common.android.view.recycler.base.BaseListItem
import com.vmedia.core.common.android.view.recycler.base.adapterDelegateViewBinding
import com.vmedia.feature.assetdetails.presentation.databinding.AssetdetailsCardDescriptionBinding
import com.vmedia.feature.assetdetails.presentation.recycler.keyword.KeywordsAdapter
import com.vmedia.feature.assetdetails.presentation.recycler.keyword.OnKeywordClickListener
import com.vmedia.feature.assetdetails.presentation.recycler.newadapter.listitem.DescriptionListItem

internal typealias OnImageClickListener = () -> Unit
internal typealias OnCollapsedDescriptionClickListener = () -> Unit

private const val MAX_COLLAPSED_DESCRIPTION_LINES: Int = 10

internal fun descriptionAdapterDelegate(
    onImageClickListener: OnImageClickListener,
    onCollapsedClickListener: OnCollapsedDescriptionClickListener,
    onKeywordClickListener: OnKeywordClickListener,
) = adapterDelegateViewBinding<DescriptionListItem, BaseListItem, AssetdetailsCardDescriptionBinding>(
    AssetdetailsCardDescriptionBinding::inflate
) {
    val keywordsAdapter = KeywordsAdapter(onKeywordClickListener)

    binding.assetdetailsDescriptionKeywordsList.init(
        adapter = keywordsAdapter,
        layoutManager = FlexboxLayoutManager(context).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            alignItems = AlignItems.STRETCH
        }
    )

    binding.assetdetailsDescriptionText.movementMethod = LinkMovementMethod.getInstance()
    binding.assetdetailsDescriptionImage.setOnClickListener(onImageClickListener)
    binding.assetdetailsDescriptionScrim.setOnClickListener(onCollapsedClickListener)
    binding.assetdetailsDescriptionViewmore.setOnClickListener(onCollapsedClickListener)

    bind {
        val asset = item.asset
        val bigImage = asset.bigImage
        val isExpanded = item.isDescriptionExpanded
        val hasLargeImage = !bigImage.isNullOrBlank()
        val hasKeywords = asset.keywords.isNotEmpty() && isExpanded

        binding.assetdetailsDescriptionImage.isVisible = hasLargeImage
        binding.assetdetailsDescriptionImage.loadImage(bigImage)

        binding.assetdetailsDescriptionKeywordsList.isVisible = hasKeywords
        binding.assetdetailsDescriptionKeywordsTitle.isVisible = hasKeywords
        if (hasKeywords) keywordsAdapter.items = asset.keywords

        binding.assetdetailsDescriptionViewmore.isVisible = !isExpanded
        binding.assetdetailsDescriptionScrim.isVisible = !isExpanded

        binding.assetdetailsDescriptionText.text = asset.description.toSpan()
        binding.assetdetailsDescriptionText.maxLines =
            if (isExpanded) Integer.MAX_VALUE
            else MAX_COLLAPSED_DESCRIPTION_LINES
    }
}
