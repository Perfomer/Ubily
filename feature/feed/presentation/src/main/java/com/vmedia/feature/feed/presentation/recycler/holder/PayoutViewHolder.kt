package com.vmedia.feature.feed.presentation.recycler.holder

import android.view.View
import androidx.core.view.isVisible
import com.vmedia.core.common.android.util.toSpan
import com.vmedia.core.common.pure.obj.event.EventInfo.EventPayout
import com.vmedia.feature.feed.presentation.R
import com.vmedia.feature.feed.presentation.recycler.FeedViewHolder
import kotlinx.android.synthetic.main.feed_item_payout.*

internal class PayoutViewHolder(
    containerView: View,
    onClick: (position: Int) -> Unit,
    onOptionsClick: (position: Int) -> Unit
) : FeedViewHolder<EventPayout>(containerView, onClick, onOptionsClick) {

    override fun bindContent(item: EventPayout) {
        val payout = item.content

        val autoPayoutText = getString(
            if (payout.auto) R.string.event_payout_auto_true
            else R.string.event_payout_auto_false
        ).toSpan()

        val payoutFailedText = getString(
            if (payout.failed) R.string.event_payout_failed_true
            else R.string.event_payout_failed_false
        ).toSpan()

        feed_item_payout_auto.text = autoPayoutText
        feed_item_payout_failed.text = payoutFailedText
        feed_item_payout_paypal.isVisible = payout.paypal
    }

}