package com.vmedia.feature.feed.presentation.recycler.holder

import android.view.View
import androidx.core.view.isVisible
import com.vmedia.core.common.obj.labelResource
import com.vmedia.core.common.util.toSpan
import com.vmedia.core.data.obj.EventInfo.EventPayout
import com.vmedia.feature.feed.R
import com.vmedia.feature.feed.presentation.recycler.FeedViewHolder
import kotlinx.android.synthetic.main.feed_item.*
import kotlinx.android.synthetic.main.feed_item_payout.*

internal class PayoutViewHolder(
    containerView: View,
    onClick: (position: Int) -> Unit,
    onOptionsClick: (position: Int) -> Unit
) : FeedViewHolder<EventPayout>(containerView, onClick, onOptionsClick) {

    override fun bindContent(item: EventPayout) {
        val payout = item.content
        val period = payout.period
        val monthName = getString(period.month.labelResource)

        val autoPayoutText = getString(
            if (payout.auto) R.string.event_payout_auto_true
            else R.string.event_payout_auto_false
        ).toSpan()

        val payoutFailedText = getString(
            if (payout.failed) R.string.event_payout_failed_true
            else R.string.event_payout_failed_false
        ).toSpan()

        val descriptionText = resources.getString(
            R.string.event_payout_text,
            item.content.amount.toString(),
            monthName,
            period.year
        ).toSpan()

        feed_item_payout_auto.text = autoPayoutText
        feed_item_payout_failed.text = payoutFailedText
        feed_item_payout_paypal.isVisible = payout.paypal
        feed_item_description.text = descriptionText
    }

}