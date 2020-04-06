package com.example.feature.feed.presentation.recycler.holder

import android.view.View
import androidx.core.view.isVisible
import com.example.feature.feed.R
import com.example.feature.feed.presentation.recycler.FeedViewHolder
import com.vmedia.core.common.util.toSpan
import com.vmedia.core.data.obj.EventInfo.EventPayout
import kotlinx.android.synthetic.main.feed_item.*
import kotlinx.android.synthetic.main.feed_item_payout.*

internal class PayoutViewHolder(
    containerView: View,
    onClick: (position: Int) -> Unit
) : FeedViewHolder<EventPayout>(containerView, onClick) {

    override fun bindContent(item: EventPayout) {
        val payout = item.content

        val autoPayoutText = getString(
            if (payout.auto) R.string.event_payout_auto_true
            else R.string.event_payout_auto_false
        ).toSpan()

        val descriptionText = resources.getString(
            R.string.event_payout_text,
            item.content.amount.toString()
        ).toSpan()

        feed_item_payout_auto.text = autoPayoutText
        feed_item_payout_paypal.isVisible = payout.paypal
        feed_item_description.text = descriptionText
    }

}