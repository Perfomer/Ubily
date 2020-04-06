package com.example.feature.feed.presentation.recycler.holder

import android.view.View
import androidx.core.view.isVisible
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.example.feature.feed.presentation.recycler.FeedViewHolder
import com.vmedia.core.common.obj.descriptionResource
import com.vmedia.core.common.util.diffedValue
import com.vmedia.core.common.util.setOnClickListener
import com.vmedia.core.common.util.sumByBigDecimal
import com.vmedia.core.common.util.toSpan
import com.vmedia.core.data.obj.EventInfo.EventSale
import com.vmedia.core.data.obj.SaleInfo
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.feed_item.*
import kotlinx.android.synthetic.main.feed_item_sale.*
import kotlinx.android.synthetic.main.feed_item_sale_item.*

internal class SaleViewHolder(
    containerView: View,
    onClick: (position: Int) -> Unit,
    private val onAssetClick: (position: Int, assetIndex: Int) -> Unit
) : FeedViewHolder<EventSale>(containerView, onClick) {

    private val saleViewHolders = listOf(
        SaleItemViewHolder(feed_item_sale_1) { safeOnClick(0) },
        SaleItemViewHolder(feed_item_sale_2) { safeOnClick(1) },
        SaleItemViewHolder(feed_item_sale_3) { safeOnClick(2) }
    )

    init {
        saleViewHolders[1].containerView.background = null
    }

    override fun bindContent(item: EventSale) {
        fun bindSale(index: Int) {
            val asset = item.sales.getOrNull(index)
            val assetViewHolder = saleViewHolders[index]

            assetViewHolder.containerView.isVisible = asset != null
            asset?.let(assetViewHolder::bind)
        }

        repeat(saleViewHolders.size, ::bindSale)

        feed_item_description.text = context.getString(
            item.type.descriptionResource,
            item.sales.size,
            item.sales.sumByBigDecimal(SaleInfo::summaryPrice).toString()
        ).toSpan()
    }

    private fun safeOnClick(assetIndex: Int) {
        if (!hasPosition) return
        onAssetClick.invoke(adapterPosition, assetIndex)
    }

}

private class SaleItemViewHolder(
    override val containerView: View,
    onClick: () -> Unit
) : LayoutContainer {

    init {
        containerView.setOnClickListener(onClick)
    }

    fun bind(item: SaleInfo) {
        feed_item_sale_price.diffedValue = "$${item.summaryPrice}"
        feed_item_sale_quantity.diffedValue = "x${item.quantity}"
        feed_item_sale_title.diffedValue = item.assetName

        feed_item_sale_icon.load(item.assetIcon) {
            crossfade(true)
            transformations(RoundedCornersTransformation(8.0f))
        }
    }

}