package com.vmedia.feature.eventdetails.presentation.viewholder

import android.content.Context
import androidx.core.view.isVisible
import com.vmedia.core.common.android.util.toSpan
import com.vmedia.core.common.pure.obj.EventType
import com.vmedia.core.common.pure.obj.event.PayoutInfo
import com.vmedia.feature.eventdetails.presentation.R
import kotlinx.android.synthetic.main.eventdetails_item_payout.*

internal class PayoutViewHolder(
    context: Context
) : EventDetailsViewHolder<PayoutInfo>(
    EventType.PAYOUT,
    context,
    R.layout.eventdetails_item_payout
) {

    override fun bind(item: PayoutInfo) {
        val autoPayoutText = context.getString(
            if (item.auto) R.string.event_payout_auto_true
            else R.string.event_payout_auto_false
        ).toSpan()

        val payoutFailedText = context.getString(
            if (item.failed) R.string.event_payout_failed_true
            else R.string.event_payout_failed_false
        ).toSpan()

        eventdetails_item_payout_auto.text = autoPayoutText
        eventdetails_item_payout_failed.text = payoutFailedText
        eventdetails_item_payout_paypal.isVisible = item.paypal
    }

}